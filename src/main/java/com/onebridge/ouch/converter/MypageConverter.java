package com.onebridge.ouch.converter;

import org.springframework.stereotype.Component;

import com.onebridge.ouch.domain.Language;
import com.onebridge.ouch.domain.Nation;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.dto.mypage.request.MypageProfileUpdateRequest;
import com.onebridge.ouch.dto.mypage.response.MypageGetProfileResponse;

@Component
public class MypageConverter {

	public MypageGetProfileResponse userToMypageGetProfileResponse(User user) {
		return new MypageGetProfileResponse(user.getNickname(), user.getGender(), user.getNation().getName(),
			user.getPhoneNumber(), user.getEmail(), user.getLanguage().getName());
	}

	public User updateUserByUpdateProfileRequest(User user, MypageProfileUpdateRequest request, Nation nation,
		Language language) {
		return user.toBuilder()
			.nickname(request.getNickname())
			.phoneNumber(request.getPhoneNumber())
			.gender(request.getGender())
			.email(request.getEmail())
			.nation(nation)
			.language(language)
			.build();
	}
}
