package com.hy.ouch.dto.mypage.request;

import com.hy.ouch.domain.enums.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MypageUserInfoUpdateRequest {

	@NotBlank(message = "Nickname is required.")
	@Size(max = 20, message = "Nickname must be 20 characters or less")
	private String nickname;

	@NotBlank(message = "Phone number is required.")
	@Size(max = 15, message = "Phone number must be 15 characters or less")
	private String phoneNumber;

	@NotNull(message = "Gender is required.")
	private Gender gender;

	@NotBlank(message = "Email is required.")
	@Size(max = 30, message = "Email must be 30 characters or less")
	@Email(message = "Email should be valid.")
	private String email;

	@NotNull(message = "Language ID is required.")
	private Long nationId;
}
