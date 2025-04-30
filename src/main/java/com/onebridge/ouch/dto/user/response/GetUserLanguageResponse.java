package com.onebridge.ouch.dto.user.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GetUserLanguageResponse {

	@NotBlank(message = "언어 이름은 필수입니다.")
	private String name;

	@NotBlank(message = "언어 코드는 필수입니다.")
	@Size(min = 2, max = 2, message = "언어 코드는 정확히 2글자여야 합니다.")
	private String code; // 언어 코드 (e.g., 'en', 'ko')
}
