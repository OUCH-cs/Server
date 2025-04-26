package com.onebridge.ouch.apiPayload.code.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MedicalRecordErrorCode implements ErrorCode {

	MEDICAL_RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "MEDICAL-RECORD401", "의료기록이 존재하지 않습니다."),
	HOSPITAL_NOT_FOUND(HttpStatus.NOT_FOUND, "MEDICAL-RECORD402", "입력한 병원이 존재하지 않습니다."),
	DEPARTMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "MEDICAL-RECORD403", "입력한 진료 과가 존재하지 않습니다."),
	MEDICAL_RECORD_USER_NOT_MATCH(HttpStatus.FORBIDDEN, "MEDICAL-RECORD404", "해당 사용자의 의료기록이 아닙니다."),
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
