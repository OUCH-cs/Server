package com.hy.ouch.dto.selfDiagnosis.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetSymptomsOfDiagnosisResponse {

	private List<String> symptoms;
}
