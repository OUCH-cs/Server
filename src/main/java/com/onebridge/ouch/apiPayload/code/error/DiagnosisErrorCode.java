package com.onebridge.ouch.apiPayload.code.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiagnosisErrorCode implements ErrorCode {

	SYMPTOM_NOT_FOUND(HttpStatus.NOT_FOUND, "DIAGNOSIS401", "입력한 증상이 존재하지 않습니다."),
	DIAGNOSIS_NOT_FOUND(HttpStatus.NOT_FOUND, "DIAGNOSIS402", "자가진단표가 존재하지 않습니다."),
	SYMPTOM_ALREADY_ADDED(HttpStatus.BAD_REQUEST, "DIAGNOSIS403", "해당 증상이 이미 존재합니다."),
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
