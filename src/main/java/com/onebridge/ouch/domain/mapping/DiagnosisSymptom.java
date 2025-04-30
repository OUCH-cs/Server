package com.onebridge.ouch.domain.mapping;

import com.onebridge.ouch.domain.SelfDiagnosis;
import com.onebridge.ouch.domain.Symptom;
import com.onebridge.ouch.domain.common.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DiagnosisSymptom extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "selfDiagnosis_id", nullable = false)
	private SelfDiagnosis selfDiagnosis;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "symptomCategory_id", nullable = false)
	private Symptom symptom;
}
