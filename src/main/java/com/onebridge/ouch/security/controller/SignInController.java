package com.onebridge.ouch.security.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onebridge.ouch.apiPayload.ApiResponse;
import com.onebridge.ouch.security.converter.SignInConverter;
import com.onebridge.ouch.security.dto.request.SignInRequest;
import com.onebridge.ouch.security.tokenManger.TokenManager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "로그인 API", description = "로그인 API입니다.")
@RestController
@RequestMapping("/users/login")
@RequiredArgsConstructor
public class SignInController {

	private final AuthenticationManager authenticationManager;
	private final TokenManager tokenManager;

	@Operation(summary = "로그인 API", description = "권한에 관계없이 1개의 API로 통합되어 있습니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> singIn(@RequestBody SignInRequest signInRequest) {
		Authentication authentication = authenticationManager.authenticate(
			SignInConverter.toAuthenticationToken(signInRequest));
		String jwt = tokenManager.writeToken(authentication);
		return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt).build();
	}
}