package com.hy.ouch.dto.user.request;

import java.time.LocalDate;

import com.hy.ouch.domain.enums.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateRequest {

	@NotBlank(message = "Login ID is required.")
	@Size(max = 20, message = "Login ID must be 20 characters or less")
	private String loginId;

	@NotBlank(message = "Password is required.")
	@Size(max = 30, message = "Password must be 30 characters or less")
	private String password;

	@NotBlank(message = "Name is required.")
	@Size(max = 30, message = "Name must be 30 characters or less")
	private String name;

	@NotBlank(message = "Nickname is required.")
	@Size(max = 20, message = "Nickname must be 20 characters or less")
	private String nickname;

	@NotBlank(message = "Phone number is required.")
	@Size(max = 15, message = "Phone number must be 15 characters or less")
	private String phoneNumber;

	@NotNull(message = "Gender is required.")
	private Gender gender;

	@NotNull(message = "Birthday is required.")
	@Past(message = "Birthday must be in the past.")
	private LocalDate birthday;

	@NotBlank(message = "Email is required.")
	@Size(max = 30, message = "Email must be 30 characters or less")
	@Email(message = "Email should be valid")
	private String email;

	@NotNull(message = "Language ID is required.")
	private Long languageId;

	@NotNull(message = "Nation ID is required.")
	private Long nationId;

}
