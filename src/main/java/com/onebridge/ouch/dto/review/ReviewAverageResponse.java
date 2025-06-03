package com.onebridge.ouch.dto.review;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewAverageResponse {
	private String hospitalYkiho;
	private Double averageScore;
}
