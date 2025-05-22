package com.onebridge.ouch.service.hospital;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import com.onebridge.ouch.dto.hospital.response.HospitalDistanceResponse;
import com.onebridge.ouch.repository.hospital.HospitalRepository;

@Service
public class HospitalSearchService {
	private final HospitalRepository hospitalRepository;

	public HospitalSearchService(HospitalRepository hospitalRepository) {
		this.hospitalRepository = hospitalRepository;
	}

	public List<HospitalDistanceResponse> searchHospitals(
		String department,
		Double lat,
		Double lng,
		int page,
		int size
	) {
		if (lat == null || lng == null) {
			throw new IllegalArgumentException("좌표(lat, lng)는 필수입니다.");
		}
		int offset = page * size;
		List<Object[]> rawList;
		if (department != null && !department.isBlank()) {
			rawList = hospitalRepository.findByDepartmentOrderByDistance(department, lat, lng, size, offset);
		} else {
			rawList = hospitalRepository.findAllOrderByDistance(lat, lng, size, offset);
		}

		List<HospitalDistanceResponse> result = new ArrayList<>();
		for (Object[] row : rawList) {
			HospitalDistanceResponse dto = new HospitalDistanceResponse();
			// row 순서는 hospital 테이블 컬럼 순서 + 마지막에 distance
			dto.setYkiho((String) row[0]);
			dto.setName((String) row[1]);
			dto.setAddress((String) row[2]);
			dto.setTel((String) row[3]);
			dto.setLat(row[4] != null ? ((Number)row[4]).doubleValue() : null);
			dto.setLng(row[5] != null ? ((Number)row[5]).doubleValue() : null);
			dto.setDistance(row[6] != null ? ((Number)row[6]).doubleValue() : null);
			result.add(dto);
		}
		return result;
	}
}
