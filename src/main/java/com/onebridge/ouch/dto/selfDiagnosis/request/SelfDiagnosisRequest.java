package com.onebridge.ouch.dto.selfDiagnosis.request;

import com.onebridge.ouch.domain.enums.SymptomDuration;
import com.onebridge.ouch.domain.enums.VisitType;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SelfDiagnosisRequest {

	@NotNull(message = "Visit type is required.")
	private VisitType visitType;

	@NotEmpty(message = "증상을 입력해주세요")
	private String symptom;

	@NotNull(message = "Symptom duration is required.")
	private SymptomDuration duration;

	@NotNull(message = "Pain severity is required.")
	@Min(value = 0, message = "통증 정도는 최소 0 이상이어야 합니다.")
	@Max(value = 10, message = "통증 정도는 최대 10 이하여야 합니다.")
	private Integer painSeverity;

	private String additionalNote;
}
