package com.onebridge.ouch.domain;

import java.util.ArrayList;
import java.util.List;

import com.onebridge.ouch.domain.common.BaseEntity;
import com.onebridge.ouch.domain.enums.SymptomDuration;
import com.onebridge.ouch.domain.enums.VisitType;
import com.onebridge.ouch.domain.mapping.SelfSymptom;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SelfDiagnosis extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private VisitType visitType;

	@OneToMany(mappedBy = "selfDiagnosis", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SelfSymptom> selfSymptomList = new ArrayList<>();

	private SymptomDuration duration;

	private Integer painSeverity;

	@Column(columnDefinition = "TEXT")
	private String additionalNote;

}
