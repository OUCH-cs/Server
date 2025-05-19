package com.onebridge.ouch.domain;

import com.onebridge.ouch.domain.common.BaseEntity;
import com.onebridge.ouch.domain.enums.SymptomDuration;
import com.onebridge.ouch.domain.enums.VisitType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SelfDiagnosis extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	// @Column(columnDefinition = "TEXT")
	// private String contents;

	// @OneToMany(mappedBy = "selfDiagnosis", cascade = CascadeType.ALL, orphanRemoval = true)
	// private List<DiagnosisSymptom> diagnosisSymptomList = new ArrayList<>();

	private String symptom;

	private VisitType visitType;

	private SymptomDuration duration;

	private Integer painSeverity;

	@Column(columnDefinition = "TEXT")
	private String additionalNote;

	public void updateSelfDiagnosis(
		String symptom,
		VisitType visitType,
		SymptomDuration duration,
		Integer painSeverity,
		String additionalNote
	) {
		this.symptom = symptom;
		this.visitType = visitType;
		this.duration = duration;
		this.painSeverity = painSeverity;
		this.additionalNote = additionalNote;
	}
}
