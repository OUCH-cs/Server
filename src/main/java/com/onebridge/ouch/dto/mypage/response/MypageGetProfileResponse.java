package com.onebridge.ouch.dto.mypage.response;

import com.onebridge.ouch.domain.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MypageGetProfileResponse {

	private String nickname;

	private Gender gender;

	private String nation;

	private String phoneNumber;

	private String email;

	private String language;
}
