package com.onebridge.ouch.domain;

import com.onebridge.ouch.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
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
public class MedicalHistory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY) //cascade = CascadeType.ALL -> medicalHistory 삭제 시 user 도 삭제됨
	// @JoinColumn(name = "user_id") //외래 키 이름을 user_id로 설정
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_medical_history_user",
		value = ConstraintMode.CONSTRAINT, foreignKeyDefinition = "ON DELETE CASCADE"))
	private User user;

	@Column(nullable = true, columnDefinition = "TEXT")
	private String disease;

	@Column(nullable = true, columnDefinition = "TEXT")
	private String allergy;

	@Column(nullable = true, columnDefinition = "TEXT")
	private Long bloodPressure;

	@Column(nullable = true, columnDefinition = "TEXT")
	private Long bloodSugar;

	@Column(nullable = true, columnDefinition = "TEXT")
	private String medicineHistory;
}
