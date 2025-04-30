package com.onebridge.ouch.dto.selfDiagnosis.response;

import java.util.List;

import com.onebridge.ouch.domain.enums.SymptomDuration;
import com.onebridge.ouch.domain.enums.VisitType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetDiagnosisByUserIdResponse {

	private Long diagnosisId;
	private VisitType visitType;
	private List<String> symptoms;
	private SymptomDuration duration;
	private Integer painSeverity;
	private String additionalNote;
	private String createdAt;
}
