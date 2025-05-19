package com.onebridge.ouch.domain;

import com.onebridge.ouch.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Summary extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//MedicalRecord → Summary 단방향으로 바꾸기로 했습니다.
	// @OneToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "medical_record_id")
	// private MedicalRecord medicalRecord;

	@Column(columnDefinition = "TEXT")
	private String contents;

	@Column(columnDefinition = "TEXT")
	private String contents_summary;

	public void updateSummary(String contents_summary) {
		this.contents_summary = contents_summary;
	}
}
