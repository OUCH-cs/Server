package com.onebridge.ouch.apiPayload.code.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VisitHistoryErrorCode implements ErrorCode {

	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEDICAL-RECORD400", "User not found."),
	VISIT_HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "MEDICAL-RECORD401", "Medical record not found."),
	HOSPITAL_NOT_FOUND(HttpStatus.NOT_FOUND, "MEDICAL-RECORD402", "Hospital not found."),
	DEPARTMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "MEDICAL-RECORD403", "Department not found."),
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
