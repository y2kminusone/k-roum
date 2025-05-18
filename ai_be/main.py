from flask import Flask, request, jsonify
from langchain_upstage import UpstageEmbeddings
from langchain_community.vectorstores import Chroma
from langchain.prompts import ChatPromptTemplate
from langchain_google_genai import ChatGoogleGenerativeAI
import os
import dotenv
import json

dotenv.load_dotenv()

app = Flask(__name__)

# Embeddings 모델 설정
us_model = UpstageEmbeddings(
    api_key=os.getenv("UPSTAGE_API_KEY"),
    model="solar-embedding-1-large"
)

# 데이터베이스 디렉터리 매핑
DB_DIRECTORIES = {
    "ko": "database/kr_database",
    "en": "database/eng_database",
    "fr": "database/fre_database",
    "zh": "database/cha_database",
    "de": "database/ger_database",
    "ja": "database/jp_database",
    "ru": "database/rus_database",
    "es": "database/spn_database"
}

# LLM 모델 설정
llm = ChatGoogleGenerativeAI(
    model=os.getenv("MODEL"),
    temperature=float(os.getenv("TEMPERATURE")),
    top_p=float(os.getenv("TOP_P"))
)


# Retriever 설정
def get_retriever(language_code):
    persist_directory = DB_DIRECTORIES.get(language_code, "database/kr_database")
    vector_store = Chroma(persist_directory=persist_directory, embedding_function=us_model)
    return vector_store.as_retriever(search_type="mmr", search_kwargs={"k": 50, "fetch_k": 100, "lambda": 0.1})


def llm_filter(query, pages):
    try:
        # LLM 프롬프트 생성
        prompt_template = ChatPromptTemplate.from_template(
            "검색 쿼리와 가장 관련 있는 결과만 반환하세요.\n\n검색 쿼리: {query}\n\n결과 목록:\n{results}"
        )

        # 페이지 목록을 문자열로 변환
        formatted_pages = "\n".join(
            f"* {page.metadata.get('contentId', 'N/A')} - {page.page_content.split("\n")[0].replace('[제목] ', '').strip()}"
            for page in pages
        )

        # LLM 호출
        prompt = prompt_template.format(query=query, results=formatted_pages)
        response = llm.invoke(prompt)

        # 🔍 LLM 응답 내용 출력
        response_text = response.content.strip()

        # 메타데이터 추출
        selected_ids = set(line.split("-")[0].strip("* ").strip() for line in response_text.split("\n") if line.strip())

        # 선택된 contentId만 반환
        return [{"contentId": page.metadata["contentId"]} for page in pages if
                page.metadata["contentId"] in selected_ids]

    except Exception as e:
        print(f"[LLM Filter Error] {e}")
        return []


@app.route("/places/search", methods=["POST"])
def search_places():
    try:
        data = request.json
        query = data["query"]
        language_code = data.get("languageCode", "ko")

        # Retriever 설정
        retriever = get_retriever(language_code)
        pages = retriever.invoke([query])

        # LLM 필터링
        filtered_metadata = llm_filter(query, pages)

        # 최종 결과 반환
        return jsonify(filtered_metadata)
    except KeyError as e:
        print(f"[KeyError] Missing required key: {e}")
        return jsonify({"error": f"Missing required key: {e}"}), 400
    except Exception as e:
        print(f"[Exception] {e}")
        return jsonify({"error": str(e)}), 500


if __name__ == "__main__":
    app.run(port=5000, debug=True)
