package com.onebridge.ouch.dto.medicalHistory.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalHistoryCreateRequest {

	@NotBlank(message = "Disease is required.")
	private String disease;

	@NotBlank(message = "Allergy is required.")
	private String allergy;

	@NotNull(message = "Blood pressure is required.")
	private Long bloodPressure;

	@NotNull(message = "Blood sugar level is required.")
	private Long bloodSugar;

	@NotBlank(message = "Medical History is required.")
	private String medicineHistory;
}
