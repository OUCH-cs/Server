package com.onebridge.ouch.dto.user.request;

import com.onebridge.ouch.domain.enums.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MypageUserInfoUpdateRequest {

	@NotBlank(message = "Nickname is mandatory")
	@Size(max = 20, message = "Nickname must be 20 characters or less")
	private String nickname;

	@NotBlank(message = "Phone number is mandatory")
	@Size(max = 15, message = "Phone number must be 15 characters or less")
	private String phoneNumber;

	@NotNull(message = "Gender is mandatory")
	private Gender gender;

	@NotBlank(message = "Email is mandatory")
	@Size(max = 30, message = "Email must be 30 characters or less")
	private String email;

	private Long nationId;
}
