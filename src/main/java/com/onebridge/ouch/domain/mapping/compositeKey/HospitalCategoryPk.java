package com.onebridge.ouch.domain.mapping.compositeKey;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class HospitalCategoryPk implements Serializable {

	private Long medicalCategoryId;
	private Long hospitalId;

	public HospitalCategoryPk() {
	}

	public HospitalCategoryPk(Long medicalCategoryId, Long hospitalId) {
		this.medicalCategoryId = medicalCategoryId;
		this.hospitalId = hospitalId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof HospitalCategoryPk))
			return false;
		HospitalCategoryPk that = (HospitalCategoryPk)o;
		return Objects.equals(medicalCategoryId, that.medicalCategoryId) &&
			Objects.equals(hospitalId, that.hospitalId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(medicalCategoryId, hospitalId);
	}
}
