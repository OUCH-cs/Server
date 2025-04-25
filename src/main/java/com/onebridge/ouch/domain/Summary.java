package com.onebridge.ouch.domain;

import com.onebridge.ouch.domain.common.BaseEntity;
import com.onebridge.ouch.domain.mapping.MedicalRecord;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Summary extends BaseEntity {

	@Id
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "medical_record_id")
	private MedicalRecord medicalRecord;

	@Column(columnDefinition = "TEXT")
	private String contents;

	@Column(columnDefinition = "TEXT")
	private String contents_summary;
}
