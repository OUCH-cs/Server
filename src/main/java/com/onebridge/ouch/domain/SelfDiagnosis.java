package com.onebridge.ouch.domain;

import java.util.ArrayList;
import java.util.List;

import com.onebridge.ouch.domain.common.BaseEntity;
import com.onebridge.ouch.domain.enums.SymptomDuration;
import com.onebridge.ouch.domain.enums.VisitType;
import com.onebridge.ouch.domain.mapping.DiagnosisSymptom;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

}
