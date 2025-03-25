package com.onebridge.ouch.dto.user.response;

import java.time.LocalDate;

import com.onebridge.ouch.domain.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AllUsersResponse {

	private Long id;

	private String loginId;

	private String password;

	private String name;

	private String nickname;

	private String phoneNumber;

	private LocalDate birthday;

	private String email;

	private String address;

	private UserStatus status;
}
