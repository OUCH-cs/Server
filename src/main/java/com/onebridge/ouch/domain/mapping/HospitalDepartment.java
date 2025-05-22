package com.onebridge.ouch.domain.mapping;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HospitalDepartment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String ykiho;                   // 암호화요양기호 (병원 식별자)
	private Long departmentCode;            // 진료과목코드
	private String departmentName;          // 진료과목명
	private Integer specialistCount;        // 전문의 수

	// 병원-진료과 unique 인덱스 (ykiho + departmentCode) 추가 권장
	// @Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"ykiho", "departmentCode"})})
}
