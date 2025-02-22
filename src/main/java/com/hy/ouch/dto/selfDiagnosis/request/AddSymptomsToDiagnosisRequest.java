package com.hy.ouch.dto.selfDiagnosis.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddSymptomsToDiagnosisRequest {

	@NotNull(message = "Please enter symptoms to add.")
	private List<String> symptoms;
}
