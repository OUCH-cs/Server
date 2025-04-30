package com.onebridge.ouch.security.dto.request;

import java.time.LocalDate;

import com.onebridge.ouch.domain.Language;
import com.onebridge.ouch.domain.Nation;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.domain.enums.Gender;
import com.onebridge.ouch.domain.enums.UserStatus;

import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(example = "kr")
	private String languageCode;
	@Schema(example = "KO")
	private String nationCode;

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
