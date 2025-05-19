package com.kroum.kroum.service;

import com.kroum.kroum.dto.request.PlaceSearchRequestDto;
import com.kroum.kroum.dto.response.ContentIdDto;
import com.kroum.kroum.dto.response.PlaceSearchResponseDto;
import com.kroum.kroum.exception.InternalServerException;
import com.kroum.kroum.exception.InvalidRequestException;
import com.kroum.kroum.repository.PlaceLanguageRepository;
import com.kroum.kroum.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final PlaceRepository placeRepository;
    private final PlaceLanguageRepository placeLanguageRepository;

    // 프론트로부터 받은 검색 요청 DTO를 추가 정보를 덧붙여서 AI 서버에게 ID 리턴해달라고 요청하는 메서드
    public List<ContentIdDto> getRecommendedPlaceIds(PlaceSearchRequestDto request) {
        String url = "http://127.0.0.1:5000/ai/search";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PlaceSearchRequestDto> entity = new HttpEntity<>(request, headers);

        // ResponseEntity 타입으로 파이썬에 요청 날리기
        // try - catch에 잡힌다면 AI 서버와 통신 자체가 안 되는 것, 안 잡힌다면 통신은 되는데 AI 서버에서 에러코드 던져주는 것
        try {
            ResponseEntity<List<ContentIdDto>> response = restTemplate.exchange(
                    url.toString(),
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<List<ContentIdDto>>() {}
            );

            // 상태코드 파싱
            HttpStatusCode statusCode = response.getStatusCode();

            // 400 에러, 500 에러 처리 << 파이썬에서 400 500으로 던져줌
            if (statusCode.is4xxClientError()) throw new InvalidRequestException("AI 서버로부터 잘못된 요청 응답을 받았습니다.");

            if (statusCode.is5xxServerError()) throw new InternalServerException("AI 서버 내부 오류");

            // 에러 안 터지면 바디 파싱해서 id들 받아주기
            List<ContentIdDto> ids = response.getBody();

            /* 파싱한 ids도 비어있으면 리스트 초기화해서 던져줌
               안 비어있다면 그대로 리턴해줌 (통신도 정상적이고, 리스트에 실제 ContentId(=placeId)들도 들어있음
             */
            if (ids == null || ids.isEmpty()) return Collections.emptyList();

            return ids;
        }

        catch (RestClientException e) {
            throw new InternalServerException("AI 서버와 통신 자체가 불가능한 상황입니다.");
        }
    }

    public List<PlaceSearchResponseDto> getPlacesByIds(List<ContentIdDto> ids) {

        List<Long> placeIds = ids.stream()
                .map(ContentIdDto::getContentId)
                .toList();

        return placeLanguageRepository.findAllDtoByPlaceIdIn(placeIds);
    }

}
