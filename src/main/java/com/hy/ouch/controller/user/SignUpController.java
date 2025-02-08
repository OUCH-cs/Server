package com.hy.ouch.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hy.ouch.apiPayload.ApiResponse;
import com.hy.ouch.service.security.SignUpService;
import com.hy.ouch.dto.user.request.SignUpRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users/signup")
@RequiredArgsConstructor
public class SignUpController {

	private final SignUpService signUpService;

	@PostMapping
	public ResponseEntity<ApiResponse<Void>> signUpPersonal(
		@RequestBody SignUpRequest signUpRequest) {
		signUpService.signUpUser(signUpRequest);
		return ResponseEntity.ok()
			.body(ApiResponse.createdWithNoData());
	}

	@GetMapping("/duplicate/id")
	public ResponseEntity<ApiResponse<Void>> checkDuplicateId(@RequestParam("id") String id) {
		signUpService.isDuplicated(id);
		return ResponseEntity.ok()
			.body(ApiResponse.successWithNoData());
	}

	@GetMapping("/duplicate/nickname")
	public ResponseEntity<ApiResponse<Void>> checkDuplicateNickName(@RequestParam("nickname") String nickname) {
		signUpService.isDuplicatedNickName(nickname);
		return ResponseEntity.ok()
			.body(ApiResponse.successWithNoData());
	}
}
