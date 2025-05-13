package com.kroum.kroum.controller;

import com.kroum.kroum.dto.response.SearchHistoryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Tag(name = "SearchHistory API", description = "최근 검색어를 가져오는 컨트롤러, 검색 창을 클릭 && 로그인이면 출력")
@RestController
@RequestMapping("/search-history")
public class SearchHistoryController {

    @Operation(summary = "최근 검색어 조회", description = "로그인된 사용자의 최근 검색어 리스트를 반환")
    @ApiResponse(responseCode = "200", description = "성공",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SearchHistoryResponseDto.class))))
    @GetMapping
    public ResponseEntity<List<SearchHistoryResponseDto>> searchHistory(HttpSession session) {

        // 나중에 세션에서 유저 정보를 가져와서 DB 조회
        // DB에서 꺼낼때 createdAt 기준으로 정렬해서 꺼내기
        List<SearchHistoryResponseDto> historyList = List.of(
                new SearchHistoryResponseDto("서울 야경 명소를 알려줘", "2025-05-10 22:31:15"),
                new SearchHistoryResponseDto("부산 해변 근처 맛집이 궁금해", "2025-05-09 18:12:44")
        );

        return ResponseEntity.status(HttpStatus.OK).body(historyList);
    }
}
