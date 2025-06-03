package com.onebridge.ouch.dto.medicalRecord.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MedicalRecordUpdateRequest {

	private LocalDate visitDate;

	private String visitingHospital;

	private String medicalSubject;

	private String symptoms;

	private String treatmentSummary;
}
