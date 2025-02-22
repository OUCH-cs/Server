package com.hy.ouch.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hy.ouch.dto.MessageResponse;
import com.hy.ouch.dto.user.response.UserInfoResponse;
import com.hy.ouch.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	//유저 조회(테스트용)
	@GetMapping("/{userId}")
	public UserInfoResponse getUserInfo(@PathVariable Long userId) {
		return userService.getUserInfo(userId);
	}

	//유저 탈퇴(비활성화)
	@PatchMapping("/{userId}")
	public ResponseEntity<MessageResponse> deactivateUser(@PathVariable Long userId) {
		userService.deactivateUser(userId);
		return ResponseEntity.ok(new MessageResponse("Your account has been deactivated."));
	}

	//유저 삭제(테스트용)
	@DeleteMapping("/{userId}")
	public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
		return ResponseEntity.ok(new MessageResponse("Your account has been deleted."));
	}

}


















