package com.hy.ouch.apiPayload.code.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiagnosisErrorCode implements ErrorCode {

	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "DIAGNOSIS400", "User not found."),
	SYMPTOM_NOT_FOUND(HttpStatus.NOT_FOUND, "DIAGNOSIS400", "Symptom not found."),
	DIAGNOSIS_NOT_FOUND(HttpStatus.NOT_FOUND, "DIAGNOSIS400", "Diagnosis not found."),
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
