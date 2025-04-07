package com.onebridge.ouch.dto.user.request;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class UserUpdateRequest {

	private String loginId;

	private String password;

	private String name;

	private String nickname;

	private String phoneNumber;

	private LocalDate birthday;

	private String email;

	private String address;
}
