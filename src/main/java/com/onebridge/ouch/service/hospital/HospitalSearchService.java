package com.onebridge.ouch.service.hospital;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.onebridge.ouch.dto.hospital.response.HospitalDistanceResponse;
import com.onebridge.ouch.repository.hospital.HospitalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HospitalSearchService {
	private final HospitalRepository hospitalRepository;
	private final RegionService regionService;

	public List<HospitalDistanceResponse> searchHospitals(
		String department,
		String type, // 종별코드명 ex. '약국', '병원'
		String sido,
		Double lat,
		Double lng,
		int page,
		int size
	) {
		if (lat == null || lng == null) throw new IllegalArgumentException("좌표(lat, lng)는 필수입니다.");

		// 1) 전달받은 sido를 한국어로 변환
		String sidoKr = regionService.getKrSidoName(sido);

		int offset = page * size;
		List<Object[]> rawList;

		// 진료과: 내과→내과,가정의학과 모두 포함
		String department1 = null;
		String department2 = null;
		if (department != null && !department.isBlank()) {
			if (department.equals("내과") || department.equals("가정의학과")) {
				department1 = "내과";
				department2 = "가정의학과";
			} else {
				department1 = department;
				department2 = department;
			}
		}

		// type(종별코드명)도 null/입력값에 따라 처리
		if ((department1 != null && department2 != null) || (type != null && !type.isBlank()) || (sidoKr != null)) {
			rawList = hospitalRepository.findWithConditionsOrderByDistance(
				lat, lng,
				type, department1, department2, sidoKr, size, offset
			);
		} else {
			rawList = hospitalRepository.findAllOrderByDistance(lat, lng, size, offset);
		}

		List<HospitalDistanceResponse> result = new ArrayList<>();
		for (Object[] row : rawList) {
			HospitalDistanceResponse dto = new HospitalDistanceResponse();
			dto.setYkiho((String) row[0]);
			dto.setName((String) row[1]);
			dto.setAddress((String) row[2]);
			dto.setTel((String) row[3]);
			dto.setLat(row[4] != null ? ((Number)row[4]).doubleValue() : null);
			dto.setLng(row[5] != null ? ((Number)row[5]).doubleValue() : null);
			dto.setType((String) row[6]);
			// 진료과 집계값은 ,로 구분된 String → List로 변환
			dto.setDepartments(row[7] != null ? Arrays.asList(((String) row[7]).split(",")) : null);
			dto.setDistance(row[8] != null ? ((Number)row[8]).doubleValue() : null);
			result.add(dto);
		}
		return result;
	}
}