package com.onebridge.ouch.dto.review;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewStatsResponse {
	private String hospitalYkiho;
	private long reviewCount;
	private double averageScore; // 리뷰가 없으면 0.0으로 설정
}
