package com.kroum.kroum.controller;

import com.kroum.kroum.dto.request.PlaceSearchRequestDto;
import com.kroum.kroum.dto.response.*;
import com.kroum.kroum.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "Place API", description = "장소 검색, 검색 결과 등 제공 해주는 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;

    @Operation(summary = "관광지 검색", description = "문장형으로 관광지를 검색함")
    @ApiResponse(
            responseCode = "200",
            description = "검색 결과 리스트",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlaceSearchResponseDto.class)))
    )
    @PostMapping("/search")
    public ResponseEntity<List<PlaceSearchResponseDto>> searchPlace(@RequestBody PlaceSearchRequestDto request) {
        List<ContentIdDto> ids = placeService.getRecommendedPlaceIds(request);
        List<PlaceSearchResponseDto> places = placeService.getPlacesByIds(ids);
        return ResponseEntity.ok(places);

    }

    @Operation(summary = "장소 상세 통합 요청", description = "장소 클릭 시 상세 정보 + 리뷰 + 주변 장소 리스트 반환")
    @ApiResponse(
            responseCode = "200",
            description = "성공 시 통합 상세 정보 반환",
            content = @Content(schema = @Schema(implementation = PlaceDetailsWithRecommendationsResponseDto.class))
    )
    @GetMapping("/{placeId}/with-everything")
    public ResponseEntity<PlaceDetailsWithRecommendationsResponseDto> getPlaceDetailsWithEverything(
            @PathVariable Long placeId,
            @RequestParam String languageCode,
            HttpSession session
    ) {
        /*// 상세 정보(리뷰 갯수, 리뷰 평점, 찜 갯수, 내 찜 여부)
        PlaceDetailsResponseDto details = placeService.getPlaceDetails(placeId, languageCode, user);

        // 타인의 리뷰 리스트 (타인 닉네임, 작성일자, 별점, 텍스트)
        List<ReviewByPlaceIdResponseDto> reviews = reviewService.getReviewsByPlaceId(placeId);

        // 추천 장소 리스트 (추천 장소 관련 데이터들)
        List<PlaceSearchResponseDto> recommendations = placeService.getRelatedPlaces(placeId, languageCode);
*/
        // 응답 객체 생성
        /*PlaceDetailsWithRecommendationsResponseDto response =
                new PlaceDetailsWithRecommendationsResponseDto(details, reviews, recommendations);*/

        return ResponseEntity.ok(null);//(response);
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

                        "경복궁",
                        "서울 도심 속 전통 궁궐",
                        "서울특별시 종로구 사직로 161"
                )
                ,
                new PlaceSearchResponseDto(
                        58.5665,
                        245.9780,
                        "https://cdn.kroum.com/images/gyungbok.jpg",

                        "장소명2",
                        "설명2",
                        "주소 2"
                )
        );

        return ResponseEntity.ok(places);
    }




    @PostMapping("/test-direct")
    public ResponseEntity<?> testDirect(@RequestBody List<Map<String, String>> contentIds) {
        // contentId만 추출해서 Long으로 변환
        List<ContentIdDto> ids = contentIds.stream()
                .map(map -> Long.parseLong(map.get("contentId")))
                .map(ContentIdDto::new)
                .toList();

        System.out.println("테스트 contentIds: " + ids);

        // 실제 서비스 호출
        List<PlaceSearchResponseDto> result = placeService.getPlacesByIds(ids);

        return ResponseEntity.ok(result);
    }

}
