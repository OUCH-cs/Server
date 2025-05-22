package com.onebridge.ouch.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hospital {

	@Id
	//@Column(name = "암호화요양기호")
	private String ykiho;               // 암호화요양기호 (PK)

	//@Column(name = "요양기관명")
	private String name;                // 병원 이름

	//@Column(name = "종별코드명")
	private String type;                // 종별코드명(병원/의원/종합병원 등)

	//@Column(name = "시도코드명")
	private String sido;                // 시/도명

	//@Column(name = "시군구코드명")
	private String sigungu;             // 시/군/구명

	//@Column(name = "읍면동")
	private String eupmyeondong;        // 읍면동

	//@Column(name = "우편번호")
	private String zipcode;             // 우편번호

	//@Column(name = "주소")
	private String address;             // 주소

	//@Column(name = "전화번호")
	private String tel;                 // 전화번호

	//@Column(name = "좌표(X)")
	private Double lat;                 // 좌표(X) = 위도

	//@Column(name = "좌표(Y)")
	private Double lng;                 // 좌표(Y) = 경도

	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// private Long id;
	//
	// @Column(nullable = false, length = 50)
	// private String name;
	//
	// @Column(nullable = false, length = 100)
	// private String address;
	//
	// @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
	// private List<HospitalDepartment> hospitalDepartmentList = new ArrayList<>();
}
