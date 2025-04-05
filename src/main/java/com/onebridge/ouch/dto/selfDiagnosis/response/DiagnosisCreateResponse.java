package com.onebridge.ouch.dto.selfDiagnosis.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiagnosisCreateResponse {

	private Long diagnosisId;
	private String message;
}
