package com.onebridge.ouch.dto.hospital.response;

import java.util.List;

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
	private String type;         // 종별코드명 (ex. '약국', '병원', '의원' 등)
	private List<String> departments; // 병원이 보유한 진료과 리스트
}
