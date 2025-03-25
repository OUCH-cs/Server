package com.onebridge.ouch.controller.user;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onebridge.ouch.dto.MessageResponse;
import com.onebridge.ouch.dto.MessageResponseDetailed;
import com.onebridge.ouch.dto.user.request.MypageUserInfoUpdateRequest;
import com.onebridge.ouch.dto.user.response.MypageUserInfoResponse;
import com.onebridge.ouch.dto.user.response.UserInfoResponse;
import com.onebridge.ouch.service.user.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	//유저 조회(테스트용)
	@GetMapping("/users/{userId}")
	public UserInfoResponse getUserInfo(@PathVariable Long userId) {
		return userService.getUserInfo(userId);
	}

	//유저 탈퇴(비활성화)
	@PatchMapping("/users/{userId}")
	public ResponseEntity<?> deactivateUser(@PathVariable Long userId) {
		userService.deactivateUser(userId);
		MessageResponse body = new MessageResponse("Your account has been deactivated.");
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_TYPE, "application/json");

		return new ResponseEntity<>(body, header, HttpStatus.OK);
	}

	//유저 삭제(테스트용)
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
		MessageResponse body = new MessageResponse("Your account has been deleted.");
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_TYPE, "application/json");

		return new ResponseEntity<>(body, header, HttpStatus.OK);
	}

	//마이페이지(내 정보) 조회
	@GetMapping("/mypage/users/{userId}")
	public MypageUserInfoResponse myPageGetUserInfo(@PathVariable Long userId) {
		return userService.myPageGetUserInfo(userId);
	}

	//내 정보 수정 (사용자로부터 모든 필드의 값을 받아 put 요청 처리)
	@PutMapping("/mypage/users/{userId}")
	public ResponseEntity<MessageResponseDetailed<String>> myPageUpdateUserInfo(@PathVariable Long userId,
		@RequestBody @Valid MypageUserInfoUpdateRequest request) {
		// System.out.println("userId = " + userId);
		userService.myPageUpdateUserInfo(userId, request);
		MessageResponseDetailed<String> body = new MessageResponseDetailed<>(
			true,
			"COMMON200",
			"요청이 성공적으로 처리되었습니다.",
			"유저 정보가 성공적으로 수정되었습니다."
		);
		return ResponseEntity.ok(body);
	}
}


















