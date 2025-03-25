package com.onebridge.ouch.dto.user.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserSignupResponse {

	private Long id;

	private LocalDate createdAt;
}
