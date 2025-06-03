package com.onebridge.ouch.service.department;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebridge.ouch.dto.hospital.response.DepartmentMappingDto;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

/**
 * 1) data/department.json 을 로딩해서 List<DepartmentMappingDto> 로 저장
 * 2) "한·영·중(any) 진료과명 → 한글(nameKr)" 매핑용 Map을 구축
 * 3) getAllDepartments() 는 기존과 동일하게 전체 리스트를 반환
 * 4) getKrDepartmentName(input) 를 통해 다국어 → 한글 매핑 기능 제공
 */
@Service
public class DepartmentService {
	// JSON에서 읽어들인 전체 진료과 리스트 (DTO 객체)
	private List<DepartmentMappingDto> departmentList;

	// key: nameKr, or lower(nameEn), or nameZh  →  nameKr
	private final Map<String, String> anyToKrDept = new HashMap<>();

	@PostConstruct
	public void init() throws Exception {
		// 1) JSON 로드
		ObjectMapper mapper = new ObjectMapper();
		InputStream is = new ClassPathResource("data/department.json").getInputStream();
		departmentList = mapper.readValue(is, new TypeReference<List<DepartmentMappingDto>>() {});

		// 2) anyToKrDept 맵 구축
		for (DepartmentMappingDto dm : departmentList) {
			String kr = dm.getNameKr().trim();
			String en = dm.getNameEn().trim().toLowerCase();
			String zh = dm.getNameZh().trim();

			// 하나의 진료과를 세 언어로 모두 매핑
			anyToKrDept.put(kr, kr);       // 한국어 입력
			anyToKrDept.put(en, kr);       // 영어(소문자) 입력
			anyToKrDept.put(zh, kr);       // 중국어 입력
		}
	}

	/**
	 * 전체 진료과 목록(한·영·중 포함) 반환
	 * 예) [{"code":1,"nameKr":"내과","nameEn":"Internal Medicine","nameZh":"内科"}, ...]
	 */
	public List<DepartmentMappingDto> getAllDepartments() {
		return Collections.unmodifiableList(departmentList);
	}

	/**
	 * 입력된 진료과명(input)을 한글 진료과명(nameKr)으로 변환
	 * - 예1) input="Internal Medicine" → return "내과"
	 * - 예2) input="内科"             → return "내과"
	 * - 예3) input="내과"             → return "내과"
	 * - 매칭 실패 시 null 반환
	 */
	public String getKrDepartmentName(String input) {
		if (input == null || input.isBlank()) {
			return null;
		}
		String key = input.trim();
		// 1) 한글 키가 있는 경우
		if (anyToKrDept.containsKey(key)) {
			return anyToKrDept.get(key);
		}
		// 2) 소문자 영어로 바꿔서 검색
		String lower = key.toLowerCase();
		return anyToKrDept.getOrDefault(lower, null);
	}


}
