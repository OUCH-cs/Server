package com.onebridge.ouch.dto.hospital.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentMappingDto {
	private Long code;
	private String nameKr;
	private String nameEn;
	private String nameZh;
}
