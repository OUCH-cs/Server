package com.onebridge.ouch.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onebridge.ouch.apiPayload.ApiResponse;
import com.onebridge.ouch.dto.language.response.GetLanguageResponse;
import com.onebridge.ouch.dto.user.response.UserInfoResponse;
import com.onebridge.ouch.security.authorization.UserId;
import com.onebridge.ouch.service.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "사용자 관련 API", description = "사용자 관련 API 입니다.")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	//유저 조회
	@GetMapping
	public ResponseEntity<ApiResponse<UserInfoResponse>> getUserInfo(
		@UserId Long userId) {
		UserInfoResponse response = userService.getUserInfo(userId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	//유저 탈퇴(비활성화)
	@DeleteMapping
	public ResponseEntity<ApiResponse<Void>> deactivateUser(
		@UserId Long userId) {
		userService.deactivateUser(userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@Operation(summary = "유저 언어 설정 변경 API", description = "언어코드를 전달받아 유저의 언어 설정을 변경합니다.")
	@PatchMapping("/languages/{languageCode}")
	public ResponseEntity<ApiResponse<Void>> updateUserLanguage(@UserId Long userId, @PathVariable("languageCode") String languageCode) {
		userService.updateUserLanguage(userId, languageCode);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@Operation(summary = "유저 설정 언어 조회 API", description = "로그인 된 유저의 설정된 언어를 조회합니다.")
	@GetMapping("/languages")
	public ResponseEntity<ApiResponse<GetLanguageResponse>> getUserLanguage(@UserId Long userId) {
		userService.getUserLanguage(userId);
		GetLanguageResponse languageResponse = userService.getUserLanguage(userId);
		return ResponseEntity.ok(ApiResponse.success(languageResponse));
	}
}
