package com.onebridge.ouch.dto.user.response;

import java.time.LocalDate;

import com.onebridge.ouch.domain.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
	private String loginId;

	private String password;

	private String name;

	private String nickname;

	private String phoneNumber;

	private Gender gender;

	private LocalDate birthday;

	private String email;

	private Long languageId;

	private Long nationId;
}
