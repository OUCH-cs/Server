package com.onebridge.ouch.dto.medicalHistory.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MedicalHistoryCreateResponse {
	private Long id;
	private String disease;
	private String allergy;
	private Long bloodPressure;
	private Long bloodSugar;
	private String medicineHistory;
}
