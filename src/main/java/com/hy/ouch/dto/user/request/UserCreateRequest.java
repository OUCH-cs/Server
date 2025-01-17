package com.hy.ouch.dto.user.request;

import java.time.LocalDate;

import com.hy.ouch.domain.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateRequest {

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
