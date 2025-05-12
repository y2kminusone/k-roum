package com.kroum.kroum.controller;

import com.kroum.kroum.dto.request.PlaceSearchRequestDto;
import com.kroum.kroum.dto.response.PlaceDetailsResponseDto;
import com.kroum.kroum.dto.response.PlaceSearchResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Place API", description = "장소 검색, 검색 결과 등 제공 해주는 컨트롤러")
@RestController
@RequestMapping("/places")
public class PlaceController {

    @Operation(summary = "관광지 검색", description = "문장형으로 관광지를 검색함")
    @ApiResponse(
            responseCode = "200",
            description = "검색 결과 리스트",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlaceSearchResponseDto.class)))
    )
    @PostMapping("/search")
    public List<PlaceSearchResponseDto> searchPlace(@RequestBody PlaceSearchRequestDto request) {

        /**
         * 실제 구현은 아래와 같이 한다. 얘는 별도의 서비스 로직을 만들 필요가 없다.
         * List<Long> ids = aiService.getRecommendedPlaceIds(request.getQuery(), request.getLanguageCode());
         *
         *     // 핵심 로직 위임
         *     return placeService.getPlacesByIds(ids, request.getLanguageCode());
         */

        // 현재는 dto 리턴이지만, 나중에 ResponseEntity 리턴해서 상태 코드랑 헤더 제어할 수 있게 구현
        // mock data 넣어서 체크
        return List.of(
                new PlaceSearchResponseDto(
                        37.5665,
                        126.9780,
                        "https://cdn.kroum.com/images/gyungbok.jpg",
                        List.of(
                                "https://cdn.kroum.com/images/gyungbok_1.jpg",
                                "https://cdn.kroum.com/images/gyungbok_2.jpg"
                        ),
                        "경복궁",
                        "서울 도심 속 전통 궁궐",
                        "서울특별시 종로구 사직로 161"
                )
                ,
                new PlaceSearchResponseDto(
                        58.5665,
                        245.9780,
                        "https://cdn.kroum.com/images/gyungbok.jpg",
                        List.of(
                                "https://cdn.kroum.com/images/gyungbok_1.jpg",
                                "https://cdn.kroum.com/images/gyungbok_2.jpg"
                        ),
                        "장소명2",
                        "설명2",
                        "주소 2"
                )

        );
    }

    @Operation(summary = "상세 정보 조회 요청", description = "검색 결과에서 특정 장소 클릭")
    @ApiResponse( // 여러 실패 상태 코드는 나중에 따로 추가
            responseCode = "200",
            description = "성공 시 장소 상세 정보 반환",
            content = @Content(schema = @Schema(implementation = PlaceDetailsResponseDto.class)
            )
    )
    @GetMapping("/{placeId}")
    public ResponseEntity<PlaceDetailsResponseDto> getPlaceDetails(@PathVariable Long placeId,
                                                                   @RequestParam String languageCode) {

        // 서비스 로직 구현하기 placeId, languageCode로 DB에서 데이터 찾아오면 됨
        return ResponseEntity.ok(new PlaceDetailsResponseDto(41, 4.5, 32, true));
    }

    @Operation(summary = "추천 장소 리스트 요청", description = "백엔드 -> api만 도는 내부 api")
    @ApiResponse(
            responseCode = "200",
            description = "성공시 추천 장소 리스트 정보 반환",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlaceDetailsResponseDto.class)))
            )
    @GetMapping
    // 리턴 타입이 list고 reponse entity들 넘겨줄 때는 **ResponseEntity<List<Dto>> 가 정석**이자 가장 많이 쓰인다.
    public ResponseEntity<List<PlaceSearchResponseDto>> getPlacesByIds(
            @RequestParam List<Long> ids,
            @RequestParam String languageCode

            // /ai/search 사용 후에 받은 ids들을 사용한다.
            // ids랑 language 코드 기반으로 추천 장소 리스트 조회한다.
            // ids는 ai에서 넘겨준 유사도가 높은 순의 k개의 id들이다.
    ) {
        // 실제 구현에서는 서비스에서 id, languageCode 파라미터로 받은 걸 db에서 조회하도록 하고
        // 각 받아온 객체들 dto list 담은거 쓰도록 한다.
        List<PlaceSearchResponseDto> places = List.of(
                new PlaceSearchResponseDto(
                        37.5665,
                        126.9780,
                        "https://cdn.kroum.com/images/gyungbok.jpg",
                        List.of(
                                "https://cdn.kroum.com/images/gyungbok_1.jpg",
                                "https://cdn.kroum.com/images/gyungbok_2.jpg"
                        ),
                        "경복궁",
                        "서울 도심 속 전통 궁궐",
                        "서울특별시 종로구 사직로 161"
                )
                ,
                new PlaceSearchResponseDto(
                        58.5665,
                        245.9780,
                        "https://cdn.kroum.com/images/gyungbok.jpg",
                        List.of(
                                "https://cdn.kroum.com/images/gyungbok_1.jpg",
                                "https://cdn.kroum.com/images/gyungbok_2.jpg"
                        ),
                        "장소명2",
                        "설명2",
                        "주소 2"
                )
        );

        return ResponseEntity.ok(places);
    }

    @Operation(summary = "주변 장소 리스트 요청", description = "유저가 추천 장소 중 특정 장소 클릭 후 선택 -> 주변 장소 리스트 출력")
    @ApiResponse(
            // 응답코드 분기는 나중에 구현
            responseCode = "200",
            description = "성공시 주변 장소 리스트 정보 반환",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlaceDetailsResponseDto.class)))
    )
    @GetMapping("/{placeId}/related")
    public ResponseEntity<List<PlaceSearchResponseDto>> getRelatedPlacesById(
            @PathVariable Long placeId,
            @RequestParam String languageCode) {

            // 서비스 로직은 id로 해당 장소의 위도 경도를 갖고와서
            // bounding box를 적용해서 그 범위에 해당하는 주변 장소 리스트를 모두 가져오도록 한다.
            //컨트롤러는 이를 리턴한다
            List<PlaceSearchResponseDto> relatedPlaces = List.of(
                    new PlaceSearchResponseDto(
                            37.5665,
                            126.9780,
                            "https://cdn.kroum.com/images/gyungbok.jpg",
                            List.of(
                                    "https://cdn.kroum.com/images/gyungbok_1.jpg",
                                    "https://cdn.kroum.com/images/gyungbok_2.jpg"
                            ),
                            "경복궁",
                            "서울 도심 속 전통 궁궐",
                            "서울특별시 종로구 사직로 161"
                    )
                    ,
                    new PlaceSearchResponseDto(
                            58.5665,
                            245.9780,
                            "https://cdn.kroum.com/images/gyungbok.jpg",
                            List.of(
                                    "https://cdn.kroum.com/images/gyungbok_1.jpg",
                                    "https://cdn.kroum.com/images/gyungbok_2.jpg"
                            ),
                            "장소명2",
                            "설명2",
                            "주소 2"
                    )
            );

            return ResponseEntity.ok(relatedPlaces);
    }
}
