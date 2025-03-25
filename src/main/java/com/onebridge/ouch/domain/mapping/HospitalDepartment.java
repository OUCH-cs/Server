package com.onebridge.ouch.domain.mapping;

import com.onebridge.ouch.domain.Hospital;
import com.onebridge.ouch.domain.Department;
import com.onebridge.ouch.domain.common.BaseEntity;
import com.onebridge.ouch.domain.mapping.compositeKey.HospitalCategoryPk;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HospitalDepartment extends BaseEntity {

	@EmbeddedId
	private HospitalCategoryPk hospitalCategoryPk;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("medicalCategoryId")  // HospitalCategoryId의 categoryId와 매핑
	//@JoinColumn(name = "medicalCategory_id", nullable = false)
	private Department department;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("hospitalId")  // HospitalCategoryId의 hospitalId와 매핑
	//@JoinColumn(name = "hospital_id", nullable = false)
	private Hospital hospital;
}
