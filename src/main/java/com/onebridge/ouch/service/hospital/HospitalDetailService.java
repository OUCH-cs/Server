package com.onebridge.ouch.service.hospital;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.onebridge.ouch.domain.Hospital;
import com.onebridge.ouch.domain.mapping.HospitalDepartment;
import com.onebridge.ouch.dto.hospital.response.HospitalDetailResponse;
import com.onebridge.ouch.repository.hospital.HospitalDepartmentRepository;
import com.onebridge.ouch.repository.hospital.HospitalRepository;

@Service
public class HospitalDetailService {
	private final HospitalRepository hospitalRepository;
	private final HospitalDepartmentRepository hospitalDepartmentRepository;

	public HospitalDetailService(HospitalRepository hospitalRepository, HospitalDepartmentRepository hospitalDepartmentRepository) {
		this.hospitalRepository = hospitalRepository;
		this.hospitalDepartmentRepository = hospitalDepartmentRepository;
	}

	public HospitalDetailResponse getHospitalDetail(String ykiho) {
		Hospital hospital = hospitalRepository.findById(ykiho)
			.orElseThrow(() -> new RuntimeException("병원 정보를 찾을 수 없습니다."));

		// 해당 병원의 진료과 + 전문의 수
		List<HospitalDepartment> deptList = hospitalDepartmentRepository.findByYkiho(ykiho);

		List<HospitalDetailResponse.DepartmentInfo> departments = deptList.stream()
			.map(d -> {
				HospitalDetailResponse.DepartmentInfo info = new HospitalDetailResponse.DepartmentInfo();
				info.setDepartmentName(d.getDepartmentName());
				info.setSpecialistCount(d.getSpecialistCount());
				return info;
			})
			.collect(Collectors.toList());

		// 상세 응답 구성
		HospitalDetailResponse detail = new HospitalDetailResponse();
		detail.setYkiho(hospital.getYkiho());
		detail.setName(hospital.getName());
		detail.setType(hospital.getType());
		detail.setZipcode(hospital.getZipcode());
		detail.setAddress(hospital.getAddress());
		detail.setLat(hospital.getLat());
		detail.setLng(hospital.getLng());
		detail.setTel(hospital.getTel());
		detail.setDepartments(departments);
		return detail;
	}
}
