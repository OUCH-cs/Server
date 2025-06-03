package com.onebridge.ouch.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequest {

	@NotBlank(message = "hospitalYkiho는 필수입니다.")
	private String hospitalYkiho;    //   String 타입으로 변경

	private String contents;

	@NotNull(message = "score는 필수입니다.")
	@Min(value = 1, message = "score는 최소 1점 이상이어야 합니다.")
	@Max(value = 5, message = "score는 최대 5점 이하이어야 합니다.")
	private Integer score;

	private String imageUrl;
}
