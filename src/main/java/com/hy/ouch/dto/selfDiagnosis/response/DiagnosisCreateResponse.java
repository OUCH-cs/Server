package com.hy.ouch.dto.selfDiagnosis.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DiagnosisCreateResponse {

	private Long id;
	private Long userId;
	private String contents;
	private List<String> selfSymptoms;

}
