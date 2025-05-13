package com.kroum.kroum.controller;

import com.kroum.kroum.dto.response.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Bookmark API", description = "북마크 관련해서 다루는 컨트롤러")
@RestController
@RequestMapping("/bookmarks")
public class BookmarkController {

    @Operation(summary = "찜 추가", description = "버튼을 누르면 찜이 추가됨")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상적으로 찜이 추가됨",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "로그인이 필요함")
    })
    @PostMapping("/{placeId}")
    public ResponseEntity<ApiResponseDto> addBookmark(@PathVariable Long placeId, HttpSession session) {
        // 찜 추가를 누르면 북마크 테이블에 bookmarked가 false -> true로 바뀌어야 함
        return ResponseEntity.ok(new ApiResponseDto(true, "찜했습니다."));

    }






}
