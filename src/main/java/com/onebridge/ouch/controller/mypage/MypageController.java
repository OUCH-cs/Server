package com.onebridge.ouch.controller.mypage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onebridge.ouch.apiPayload.ApiResponse;
import com.onebridge.ouch.dto.mypage.request.MypageProfileUpdateRequest;
import com.onebridge.ouch.dto.mypage.response.MypageGetProfileResponse;
import com.onebridge.ouch.security.authorization.UserId;
import com.onebridge.ouch.service.mypage.MypageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "마이페이지 API", description = "마이페이지 API")
@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

	private final MypageService mypageService;

	//마이페이지-프로필 조회
	@Operation(summary = "프로필 조회 API", description = "프로필 조회 API 입니다.")
	@GetMapping("/profile")
	public ResponseEntity<ApiResponse<MypageGetProfileResponse>> mypageGetProfile(@UserId Long userId) {
		MypageGetProfileResponse response = mypageService.mypageGetProfile(userId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	//마이페이지-프로필 수정
	@Operation(summary = "프로필 수정 API", description = "프로필 수정 API 입니다.")
	@PutMapping("/profile")
	public ResponseEntity<ApiResponse<Void>> mypageUpdateProfile(
		@RequestBody @Valid MypageProfileUpdateRequest request,
		@UserId Long userId
	) {
		mypageService.mypageUpdateProfile(userId, request);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}
