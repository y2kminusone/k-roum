package com.kroum.kroum.controller;

import com.kroum.kroum.dto.request.*;
import com.kroum.kroum.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User API", description = "회원가입, 로그인, 마이페이지 조회 등에 사용하는 컨트롤러")
@RestController
@RequestMapping("/users")
public class UserController {

    @Operation(summary = "회원가입 요청", description = "회원가입 양식을 채워서 회원가입을 요청")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (형식 오류 등)"),
            @ApiResponse(responseCode = "409", description = "중복된 이메일 또는 닉네임"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signUp(@RequestBody SignupRequestDto request) {
        // 실제 구현에서는 서비스 로직을 호출하고 서비스 로직에서 받아온 결과를 바탕으로 ApiResponseDto를 넘겨준다
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponseDto(true, "회원가입이 완료되었습니다."));
    }


    @Operation(summary = "로그인 요청", description = "아이디, 비밀번호를 제시해서 로그인을 요청")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "잘못된 요청 (형식 오류 등)"), // 나중에 디테일하게 분기 할 필요있음
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody LoginRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDto(true, "로그인에 성공하였습니다."));

    }


    @Operation(summary = "로그아웃 요청", description = "로그인 된 사람에 한정해 로그아웃 요청")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "잘못된 요청 (형식 오류 등)"), // 나중에 디테일하게 분기 할 필요있음
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDto> logout(HttpSession session) {
        session.invalidate(); // 이건 서비스로직에서 구현해야할 내용

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDto(true, "로그아웃에 성공하였습니다."));

    }


    @Operation(summary = "프로필 조회 요청", description = "이름, 이메일 등을 요청")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 조회 성공",
                    content = @Content(schema = @Schema(implementation = ProfileResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "로그인 필요"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getProfile(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        // 실제 구현에서는 null 체크 후 예외 처리도 해야 함
        // 예: if (userId == null) throw new UnauthenticatedException();

        // 서비스 로직은 userId를 기반으로 DB에서 유저 정보 조회해서 DTO 리턴
        ProfileResponseDto response = new ProfileResponseDto("홍길동", "gildong@example.com");

        return ResponseEntity.ok(response);
    }


    @Operation(summary = "프로필 수정 요청", description = "이름, 이메일 등을 수정 요청")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 수정 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "로그인 필요"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PutMapping("/profile")
    public ResponseEntity<ApiResponseDto> updateProfile(@RequestBody ProfileUpdateRequestDto request) {
        // 서비스 구현은 나중에~

        return ResponseEntity.ok(new ApiResponseDto(true, "프로필 수정에 성공했습니다."));

    }


    @Operation(summary = "마이페이지 조회", description = "프로필, 찜 목록, 리뷰 목록을 통합 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "마이페이지 데이터 반환 성공",
                    content = @Content(schema = @Schema(implementation = MyPageResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "로그인 필요"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @GetMapping("/mypage")
    public ResponseEntity<MyPageResponseDto> getMyPage(HttpSession session) {

        // 더미 Profile
        ProfileResponseDto profile = new ProfileResponseDto("대영123", "user@example.com");

        // 더미 북마크 리스트
        List<BookmarkResponseDto> bookmarks = List.of(
                new BookmarkResponseDto(1L, "경복궁", "2025-05-12", "https://cdn.kroum.com/places/gyungbok.jpg"),
                new BookmarkResponseDto(2L, "한강공원", "2025-05-10", "https://cdn.kroum.com/places/hanriver.jpg")
        );

        // 더미 리뷰 리스트
        List<ReviewSummaryResponseDto> reviews = List.of(
                new ReviewSummaryResponseDto(1L, "https://cdn.kroum.com/places/gyungbok.jpg", 4.5, "경복궁"),
                new ReviewSummaryResponseDto(2L, "https://cdn.kroum.com/places/ddp.jpg", 4.0, "동대문디자인플라자")
        );

        // 응답 객체 생성
        MyPageResponseDto response = new MyPageResponseDto(profile, bookmarks, reviews);

        return ResponseEntity.ok(response);
    }


    @Operation(summary = "아이디 찾기 요청", description = "이메일을 입력하면 해당 이메일과 맵핑된 아이디를 이메일로 보내준다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이메일 조회 성공",
                    content = @Content(schema = @Schema(implementation = FindIdByEmailResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "해당 이메일 없음"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PostMapping("/find-id")
    public ResponseEntity<FindIdByEmailResponseDto> findIdByEmail(@RequestBody FindIdByEmailRequestDto request) {
        // 서비스 로직

        return ResponseEntity.ok(new FindIdByEmailResponseDto("loginIdReturn123"));
    }


    @Operation(summary = "비밀번호 초기화 요청", description = "로그인 id와 이메일을 제시하면 리셋한 비밀번호를 이메일로 보내준다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 id와 이메일 모두 맵핑되는 레코드가 존재",
            content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "맵핑 ㄴㄴ"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponseDto> resetPassword(@RequestBody PasswordResetRequestDto request) {
        // 서비스 로직

        return ResponseEntity.ok(new ApiResponseDto(true, "이메일로 임시 비밀번호를 발송하였습니다."));
    }


    @Operation(summary = "비밀번호 변경 요청", description = "로그인 상태이고 기존 비번, 새 비번 제시시 변경")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비번 변경 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PutMapping("/change-password")
    public ResponseEntity<ApiResponseDto> updatePassword(@RequestBody PasswordChangeRequestDto request) {
        // 서비스 로직

        return ResponseEntity.ok(new ApiResponseDto(true, "비밀번호 변경에 성공하였습니다."));
    }


    @Operation(summary = "이메일 중복 확인 요청", description = "제시한 이메일이 DB에 중복 존재하는지 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "중복 없음 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "중복된 이메일"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @GetMapping("/check-email/{email}")
    public ResponseEntity<ApiResponseDto> checkEmail(@RequestParam("email") String email) {

        return ResponseEntity.ok(new ApiResponseDto(true, "사용가능한 이메일입니다."));
    }


    @Operation(summary = "닉네임 중복 확인 요청", description = "제시한 닉네임이 DB에 중복 존재하는지 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "중복 없음 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "중복된 닉네임"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @GetMapping("/check-nickname/{nickname}")
    public ResponseEntity<ApiResponseDto> checkNickname(@RequestParam("nickname") String nickname) {

        return ResponseEntity.ok(new ApiResponseDto(true, "사용가능한 닉네임입니다."));
    }


    @Operation(summary = "아이디 중복 확인 요청", description = "제시한 아이디가 DB에 중복 존재하는지 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "중복 없음 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "중복된 아이디"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @GetMapping("/check-loginId/{loginId}")
    public ResponseEntity<ApiResponseDto> checkLoginId(@RequestParam("loginId") String loginId) {

        return ResponseEntity.ok(new ApiResponseDto(true, "사용가능한 아이디입니다."));
    }

    @Operation(summary = "회원 탈퇴 요청", description = "로그인된 사용자의 계정을 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 필요"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @DeleteMapping
    public ResponseEntity<ApiResponseDto> deleteUser(HttpSession session) {
        // 실제 구현 시: 세션에서 userId 꺼내서 회원 삭제
        session.invalidate(); // 세션 제거

        return ResponseEntity.ok(new ApiResponseDto(true, "회원 탈퇴가 완료되었습니다."));
    }
}
