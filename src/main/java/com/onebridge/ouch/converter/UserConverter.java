package com.onebridge.ouch.converter;

import org.springframework.stereotype.Component;

import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.dto.user.response.UserInfoResponse;

@Component
public class UserConverter {

	public UserInfoResponse convertToUserInfoResponse(User user) {
		return new UserInfoResponse(user.getLoginId(), user.getPassword(), user.getName(), user.getNickname(),
			user.getPhoneNumber(), user.getGender(), user.getBirthday(), user.getEmail(), user.getLanguage().getId(),
			user.getNation().getId());
	}
}
