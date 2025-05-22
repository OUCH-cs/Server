package com.onebridge.ouch.dto.hospital.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalDistanceResponse {
	private String ykiho;
	private String name;
	private String address;
	private String tel;
	private Double lat;
	private Double lng;
	private Double distance; //km
}
