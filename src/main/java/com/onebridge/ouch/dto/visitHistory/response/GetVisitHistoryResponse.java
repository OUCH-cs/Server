package com.onebridge.ouch.dto.visitHistory.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetVisitHistoryResponse {

	private Long id;
	private String visitDate;
	private String visitingHospital;
	private String medicalSubject;
	private String symptoms;
	private String treatmentSummary;
}
