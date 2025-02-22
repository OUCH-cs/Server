package com.hy.ouch.controller.mypage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hy.ouch.dto.MessageResponse;
import com.hy.ouch.dto.mypage.request.MypageUserInfoUpdateRequest;
import com.hy.ouch.dto.user.response.MypageUserInfoResponse;
import com.hy.ouch.service.mypage.MypageService;
import com.hy.ouch.service.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

	private final UserService userService;
	private final MypageService mypageService;

	//마이페이지(내 정보) 조회
	@GetMapping("/{userId}")
	public MypageUserInfoResponse myPageGetUserInfo(@PathVariable Long userId) {
		return mypageService.myPageGetUserInfo(userId);
	}

	//내 정보 수정 (사용자로부터 모든 필드의 값을 받아 put 요청 처리)
	@PutMapping("/{userId}")
	public ResponseEntity<MessageResponse> myPageUpdateUserInfo(@PathVariable Long userId,
		@RequestBody @Valid MypageUserInfoUpdateRequest request) {
		mypageService.myPageUpdateUserInfo(userId, request);
		return ResponseEntity.ok(new MessageResponse("Your info has been updated."));
	}
}
