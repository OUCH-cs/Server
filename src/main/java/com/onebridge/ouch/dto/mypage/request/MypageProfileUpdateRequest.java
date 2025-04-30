package com.onebridge.ouch.dto.mypage.request;

import com.onebridge.ouch.domain.enums.Gender;

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
	private Long nationId; //사용자가 직접 입력 or 드랍다운에서 선택..?

	@NotBlank(message = "Phone number is mandatory.")
	private String phoneNumber;

	@NotBlank(message = "Email is mandatory.")
	private String email;

	@NotNull(message = "Language is mandatory.")
	private Long languageId; //사용자가 직접 입력 or 드랍다운에서 선택..?
}
