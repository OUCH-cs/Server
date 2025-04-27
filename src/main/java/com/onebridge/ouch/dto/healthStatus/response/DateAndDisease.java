package com.onebridge.ouch.dto.healthStatus.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DateAndDisease {

	private Long id;
	private String date;
	private String disease;
}
