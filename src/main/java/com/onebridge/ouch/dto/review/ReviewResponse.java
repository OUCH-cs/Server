package com.onebridge.ouch.dto.review;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {

	private Long id;
	private String userNickname;    // userId 대신 닉네임
	private String hospitalYkiho;   //   String 타입으로 변경
	private String contents;
	private Integer score;
	private String imageUrl;
	private String createdAt;   // BaseEntity에 만든 createdAt 필드(예: ISO 포맷 문자열)
	private String updatedAt;  // BaseEntity에 만든 modifiedAt 필드
}
