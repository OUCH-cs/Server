package com.onebridge.ouch.dto.visitHistory.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VisitHistoryUpdateResponse {

	private Long id;
	private LocalDate visitDate;
	private String visitingHospital;
	private String medicalSubject;
	private String symptoms;
	private String treatmentSummary;
}
