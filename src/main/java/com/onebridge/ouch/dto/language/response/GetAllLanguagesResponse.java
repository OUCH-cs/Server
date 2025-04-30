package com.onebridge.ouch.dto.language.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAllLanguagesResponse {

	private String name;

	private String code; // 언어 코드 (e.g., 'en', 'ko')
}
