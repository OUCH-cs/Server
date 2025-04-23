package com.onebridge.ouch.domain.mapping;

import java.time.LocalDate;

import com.onebridge.ouch.domain.Summary;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.domain.common.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class VisitHistory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private LocalDate visitDate;

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "hospital_id")
	private String hospital;

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "department_id")
	private String department;

	private String symptoms;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "summary_id")
	private Summary summary;
}
