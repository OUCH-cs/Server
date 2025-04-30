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
import com.onebridge.ouch.dto.user.response.UserInfoResponse;
import com.onebridge.ouch.security.authorization.UserId;
import com.onebridge.ouch.service.user.UserService;

import lombok.RequiredArgsConstructor;

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

	@PatchMapping("/languages")
	public ResponseEntity<ApiResponse<Void>> updateUserLanguage(@AuthenticationPrincipal OuchUserDetails userDetails, @PathVariable("languageId") Long id,
		@RequestBody String language) {
		Long userId = userDetails.getDatabaseId();
		userService.updateUserLanguage(userId, language);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	@GetMapping("/languages")
	public ResponseEntity<ApiResponse<?>> getUserLanguage(@AuthenticationPrincipal OuchUserDetails userDetails) {
		Long userId = userDetails.getDatabaseId();
		userService.updateLanguage(userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}


















