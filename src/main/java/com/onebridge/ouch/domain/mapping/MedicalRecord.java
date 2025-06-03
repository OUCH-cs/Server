package com.onebridge.ouch.domain.mapping;

import java.time.LocalDate;

import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.domain.common.BaseEntity;

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
public class MedicalRecord extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private LocalDate visitDate;

	private String hospital;

	private String department;

	private String symptoms;

	private String summary;

	// @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	// @JoinColumn(name = "summary_id")
	// private Summary summary;

	public void updateMedicalRecord(
		LocalDate visitDate,
		String hospital,
		String department,
		String symptoms,
		String summary
	) {
		this.visitDate = visitDate;
		this.hospital = hospital;
		this.department = department;
		this.symptoms = symptoms;
		this.summary = summary;
	}
}
