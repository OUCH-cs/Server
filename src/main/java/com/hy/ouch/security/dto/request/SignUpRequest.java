package com.hy.ouch.security.dto.request;

import java.time.LocalDate;

import com.hy.ouch.domain.Language;
import com.hy.ouch.domain.Nation;
import com.hy.ouch.domain.User;
import com.hy.ouch.domain.enums.Gender;
import com.hy.ouch.domain.enums.UserStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {

	private String loginId;
	private String password;
	private String name;
	private String nickname;
	private String phoneNumber;
	private Gender gender;
	private LocalDate birthday;
	private String email;
	private String address;
	private UserStatus status;
	private Long languageId;
	private Long nationId;

	public User toEntity(String encodedPassword, Language language, Nation nation) {
		return User.builder()
			.loginId(loginId)
			.password(encodedPassword)
			.name(name)
			.nickname(nickname)
			.phoneNumber(phoneNumber)
			.gender(gender)
			.birthday(birthday)
			.email(email)
			.address(address)
			.status(UserStatus.ACTIVE)
			.language(language)
			.nation(nation)
			.build();
	}
}
