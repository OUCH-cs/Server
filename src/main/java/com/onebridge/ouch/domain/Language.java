package com.onebridge.ouch.domain;

import com.onebridge.ouch.domain.common.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Language extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//언어 이름 - ex) 한국어, 영어
	@Column(nullable = false, length = 30)
	private String name;
	//국제 언어 코드
	private String code;

	public void updateLanguageFields(String name, String code) {
		this.name = name;
		this.code = code;
	}
}
