package com.onebridge.ouch.controller.hospital;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.onebridge.ouch.dto.hospital.response.AllDepartmentResponse;
import com.onebridge.ouch.dto.hospital.response.HospitalDetailResponse;
import com.onebridge.ouch.dto.hospital.response.HospitalDistanceResponse;
import com.onebridge.ouch.service.department.DepartmentService;
import com.onebridge.ouch.service.hospital.HospitalDetailService;
import com.onebridge.ouch.service.hospital.HospitalSearchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "병원 API", description = "병원 관련 API 입니다.")
@RequestMapping("/hospitals")
@RequiredArgsConstructor
public class HospitalController {
	private final HospitalSearchService hospitalSearchService;
	private final HospitalDetailService hospitalDetailService;
	private final DepartmentService departmentService;

	@Operation(summary = "거리 순 병원 조회 API", description = "입력된 진료과(department), 종별코드명(type) 위도(lat), 경도(lng)를 기준으로 병원 목록을 거리 순으로 조회합니다. "
		+ "진료과나 종별코드명를 입력하지 않으면 입력된 위도, 경도를 기준으로 모든 병원 목록을 거리 순으로 조회합니다. 위도, 경도는 필수로 입력해야 합니다. "
	    + "진료과나 종별코드명은 선택적으로 입력 가능합니다. 진료과는 한글로만 입력이 가능하며, 종별코드명은 병원(약국 제외 모든 병원), 약국 두 가지의 키워드로만 입력이 됩니다."
		+ "진료과가 존재하지 않는 경우가 있는데 이는 해당 병원이 진료과를 제공하지 않기 때문이며, 응답 필드 중에 department 뿐만 아니라 type(치과의원, 종합병원 등)을 진료과처럼 사용하면 웬만하면 알아볼 수 있습니다.")
	@GetMapping("/search")
	public List<HospitalDistanceResponse> searchHospitals(
		@RequestParam(required = false) String department,
		@RequestParam(required = false) String type, // 종별코드명
		@RequestParam Double lat,
		@RequestParam Double lng,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size
	) {
		return hospitalSearchService.searchHospitals(department, type, lat, lng, page, size);
	}

	@Operation(summary = "병원 상세 조회 API", description = "입력된 병원 고유ID를 통해 병원 상세 정보를 조회합니다.")
	@GetMapping("/{ykiho}")
	public HospitalDetailResponse getHospitalDetail(@PathVariable String ykiho) {
		return hospitalDetailService.getHospitalDetail(ykiho);
	}

	@Operation(summary = "진료과 목록 조회 API", description = "모든 진료과 목록을 조회합니다.")
	@GetMapping("/departments")
	public List<AllDepartmentResponse> getDepartments() {
		return departmentService.getAllDepartments();
	}
}
