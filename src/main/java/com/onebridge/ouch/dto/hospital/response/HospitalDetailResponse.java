package com.onebridge.ouch.dto.hospital.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalDetailResponse {
	private String ykiho;
	private String name;
	private String type;         // 종별코드명
	private String zipcode;
	private String address;
	private Double lat;
	private Double lng;
	private String tel;
	private List<DepartmentInfo> departments;

	// Getter/Setter
	@Getter
	@Setter
	public static class DepartmentInfo {
		private String departmentName;
		private Integer specialistCount; // 전문의 수

		// Getter/Setter
	}
}
