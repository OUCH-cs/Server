package com.onebridge.ouch.dto.selfDiagnosis.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AddSymptomsToDiagnosisRequest {

	@NotNull(message = "Please enter symptoms to add.")
	private List<String> symptoms;
}
