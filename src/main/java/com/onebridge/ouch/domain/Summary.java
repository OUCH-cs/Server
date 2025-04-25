package com.onebridge.ouch.domain;

import com.onebridge.ouch.domain.common.BaseEntity;
import com.onebridge.ouch.domain.mapping.MedicalRecord;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Summary extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "medical_record_id")
	private MedicalRecord medicalRecord;

	@Column(columnDefinition = "TEXT")
	private String contents;

	@Column(columnDefinition = "TEXT")
	private String contents_summary;
}
