package com.onebridge.ouch.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.onebridge.ouch.domain.common.BaseEntity;
import com.onebridge.ouch.domain.enums.Gender;
import com.onebridge.ouch.domain.enums.UserStatus;
import com.onebridge.ouch.domain.mapping.MedicalRecord;
import com.onebridge.ouch.security.authority.OuchAuthority;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String loginId;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(nullable = false)
	private LocalDate birthday;

	@Column(nullable = false)
	private String email;

	private String address;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "VARCHAR(8) DEFAULT 'ACTIVE'")
	private UserStatus status;

	private LocalDate inactiveDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "language_id", referencedColumnName = "id") // language_id를 외래 키로 설정
	private Language language;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nation_id", referencedColumnName = "id") // language_id를 외래 키로 설정
	private Nation nation;

	/*
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private HealthStatus healthStatus;
	*/ // 단방향 설계로 수정

	// @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	// private List<Terms> termsList = new ArrayList<>();

	// @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	// private List<Notification> notificationList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Review> reviewList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<SelfDiagnosis> selfDiagnosisList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<MedicalRecord> medicalRecordList = new ArrayList<>();

	public OuchAuthority getAuthority() {
		return OuchAuthority.INDIVIDUAL;
	}

	public void updateLanguage(Language language) {
		this.language = language;
	}

	public void updateNation(Nation nation) {
		this.nation = nation;
	}
}
