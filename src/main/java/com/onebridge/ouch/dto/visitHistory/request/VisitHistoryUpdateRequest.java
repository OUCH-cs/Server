package com.onebridge.ouch.dto.visitHistory.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class VisitHistoryUpdateRequest {

	@NotNull(message = "Visit date is required.")
	private LocalDate visitDate;

	@NotBlank(message = "Visiting hospital is required.")
	private String visitingHospital;

	@NotBlank(message = "Medical subject is required.")
	private String medicalSubject;

	@NotBlank(message = "Symptoms are required.")
	private String symptoms;

	@NotBlank(message = "Treatment summary is required.")
	private String treatmentSummary;
}
