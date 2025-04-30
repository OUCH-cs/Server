package com.hy.ouch.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hy.ouch.apiPayload.ApiResponse;
import com.hy.ouch.security.userDetail.OuchUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

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
