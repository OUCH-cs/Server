package com.onebridge.ouch.domain.mapping.compositeKey;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class DiagnosisSymptomPK implements Serializable {

	private Long selfDiagnosisId;
	private Long symptomCategoryId;

	public DiagnosisSymptomPK() {
	}

	public DiagnosisSymptomPK(Long selfDiagnosisId, Long symptomCategoryId) {
		this.selfDiagnosisId = selfDiagnosisId;
		this.symptomCategoryId = symptomCategoryId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof DiagnosisSymptomPK))
			return false;
		DiagnosisSymptomPK that = (DiagnosisSymptomPK)o;
		return Objects.equals(selfDiagnosisId, that.selfDiagnosisId) &&
			Objects.equals(symptomCategoryId, that.symptomCategoryId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(selfDiagnosisId, symptomCategoryId);
	}
}
