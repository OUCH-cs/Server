package com.hy.ouch.dto.selfDiagnosis.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiagnosisCreateRequest {

	@NotNull(message = "User Id is required.")
	private Long userId;

	@NotBlank(message = "Contents are required.")
	private String contents;

	@NotNull(message = "Symptoms are required.")
	private List<String> selfSymptoms;
}
