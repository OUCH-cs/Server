package com.onebridge.ouch.dto.mypage.request;

import com.onebridge.ouch.domain.enums.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MypageProfileUpdateRequest {

	@NotBlank(message = "Nickname is mandatory.")
	private String nickname;

	@NotNull(message = "Gender is mandatory.")
	private Gender gender;

	@NotNull(message = "Nation is mandatory.")
	@Schema(example = "KO")
	private String nationCode;

	@NotBlank(message = "Phone number is mandatory.")
	private String phoneNumber;

	@NotBlank(message = "Email is mandatory.")
	private String email;

	// @NotNull(message = "Language is mandatory.")
	// @Schema(example = "kr")
	// private String languageCode;
}
