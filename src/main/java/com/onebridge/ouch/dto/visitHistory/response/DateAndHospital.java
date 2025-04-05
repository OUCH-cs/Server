package com.onebridge.ouch.dto.visitHistory.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DateAndHospital {

	private Long id;
	private String date;
	private String hospital;
}
