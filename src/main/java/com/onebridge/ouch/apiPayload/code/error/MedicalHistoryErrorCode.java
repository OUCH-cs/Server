package com.onebridge.ouch.apiPayload.code.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MedicalHistoryErrorCode implements ErrorCode {

	MEDICAL_HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "HEALTH-STATUS401", "건강상태가 존재하지 않습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
