package com.onebridge.ouch.domain;

import com.onebridge.ouch.domain.common.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MedicalHistory extends BaseEntity {

	@Id
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@MapsId // User의 id를 MedicalHistory의 기본 키로 매핑
	@JoinColumn(name = "user_id") //외래 키 이름을 user_id로 설정
	private User user;

	@Column(nullable = true, columnDefinition = "TEXT")
	private String contents;
}
