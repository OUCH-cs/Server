package com.onebridge.ouch.dto.healthStatus.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class HealthStatusCreateRequest {

	private String disease;

	private String allergy;

	private Long bloodPressure;

	private Long bloodSugar;

	private String medicineHistory;
}
