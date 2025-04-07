package com.onebridge.ouch.dto.medicalHistory.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetMedicalHistoryResponse {

	private Long id;
	private String disease;
	private String allergy;
	private Long bloodPressure;
	private Long bloodSugar;
	private String medicineHistory;
}
