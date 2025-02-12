package com.hy.ouch.converter;

import org.springframework.stereotype.Component;

import com.hy.ouch.domain.User;
import com.hy.ouch.dto.user.response.MypageUserInfoResponse;
import com.hy.ouch.dto.user.response.UserInfoResponse;
import com.hy.ouch.dto.user.response.UserSignupResponse;

@Component
public class UserConverter {

	public UserSignupResponse user2UserSignupResponse(User user) {
		return new UserSignupResponse(user.getId(), user.getCreatedAt());
	}

	public UserInfoResponse user2UserInfoResponse(User user) {
		return new UserInfoResponse(user.getLoginId(), user.getPassword(), user.getName(), user.getNickname(),
			user.getPhoneNumber(), user.getGender(), user.getBirthday(), user.getEmail(), user.getLanguage().getId(),
			user.getNation().getId());
	}

	public MypageUserInfoResponse user2MypageUserInfoResponse(User user) {
		return new MypageUserInfoResponse(user.getNickname(), user.getEmail(), user.getLanguage().getId());
	}
}
