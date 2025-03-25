package com.onebridge.ouch.domain.mapping;

import com.onebridge.ouch.domain.SelfDiagnosis;
import com.onebridge.ouch.domain.Symptom;
import com.onebridge.ouch.domain.common.BaseEntity;
import com.onebridge.ouch.domain.mapping.compositeKey.DiagnosisSymptomPK;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SelfSymptom extends BaseEntity {

	@EmbeddedId
	private DiagnosisSymptomPK diagnosisSymptomPk;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("selfDiagnosisId")
	//@JoinColumn(name = "selfDiagnosis_id", nullable = false)
	private SelfDiagnosis selfDiagnosis;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("symptomCategoryId")
	//@JoinColumn(name = "symptomCategory_id", nullable = false)
	private Symptom symptom;
}
