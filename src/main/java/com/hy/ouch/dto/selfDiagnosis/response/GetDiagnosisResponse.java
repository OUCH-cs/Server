package com.hy.ouch.dto.selfDiagnosis.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDiagnosisResponse {

	private Long userId;
	private String contents;
	private List<String> selfSymptoms;
}
