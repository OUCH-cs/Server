package com.onebridge.ouch.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onebridge.ouch.apiPayload.ApiResponse;
import com.onebridge.ouch.dto.user.response.UserInfoResponse;
import com.onebridge.ouch.security.userDetail.OuchUserDetails;
import com.onebridge.ouch.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	//유저 조회(테스트용)
	// @GetMapping("/{userId}") //테스트를 위해 주석으로 남겨두겠습니다.
	@GetMapping
	public ResponseEntity<ApiResponse<UserInfoResponse>> getUserInfo(
		// @PathVariable Long userId
		@AuthenticationPrincipal OuchUserDetails userDetails) {
		Long userId = userDetails.getDatabaseId();
		UserInfoResponse response = userService.getUserInfo(userId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	//유저 탈퇴(비활성화)
	// @PatchMapping("/{userId}")
	@PatchMapping
	public ResponseEntity<ApiResponse<Void>> deactivateUser(
		// @PathVariable Long userId
		@AuthenticationPrincipal OuchUserDetails userDetails) {
		Long userId = userDetails.getDatabaseId();
		userService.deactivateUser(userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}

	//유저 삭제(테스트용)
	// @DeleteMapping("/{userId}")
	@DeleteMapping
	public ResponseEntity<ApiResponse<Void>> deleteUser(
		// @PathVariable Long userId
		@AuthenticationPrincipal OuchUserDetails userDetails) {
		Long userId = userDetails.getDatabaseId();
		userService.deleteUser(userId);
		return ResponseEntity.ok(ApiResponse.successWithNoData());
	}
}


















