package com.hy.ouch.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.hy.ouch.domain.common.BaseEntity;
import com.hy.ouch.domain.enums.Gender;
import com.hy.ouch.domain.enums.UserStatus;
import com.hy.ouch.domain.mapping.VisitHistory;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 20)
	private String loginId;

	@Column(nullable = false, length = 30)
	private String password;

	@Column(nullable = false, length = 30)
	private String name;

	@Column(nullable = false, length = 20)
	private String nickname;

	@Column(nullable = false, length = 15)
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(6)")
	private Gender gender;

	@Column(nullable = false)
	private LocalDate birthday;

	@Column(nullable = false, length = 30)
	private String email;

	@Column(nullable = true, length = 100)
	private String address;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, columnDefinition = "VARCHAR(8) DEFAULT 'ACTIVE'")
	private UserStatus status;

	private LocalDate inactiveDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "language_id", referencedColumnName = "id") // language_id를 외래 키로 설정
	private Language language;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nation_id", referencedColumnName = "id") // language_id를 외래 키로 설정
	private Nation nation;

	// @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	// private List<Terms> termsList = new ArrayList<>();
	//
	// @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	// private List<Notification> notificationList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Review> reviewList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<SelfDiagnosis> selfDiagnosisList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<VisitHistory> visitHistoryList = new ArrayList<>();
}
