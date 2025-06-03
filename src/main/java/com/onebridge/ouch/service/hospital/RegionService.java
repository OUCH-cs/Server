package com.onebridge.ouch.service.hospital;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebridge.ouch.dto.hospital.response.RegionMappingDto;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegionService {
	// 언어별 키(소문자) → 한국어 시도명 맵
	private final Map<String, String> enToKr = new HashMap<>();
	private final Map<String, String> zhToKr = new HashMap<>();
	private final Map<String, String> krToKr = new HashMap<>();

	@PostConstruct
	public void init() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		InputStream is = new ClassPathResource("data/regions.json").getInputStream();
		List<RegionMappingDto> list = mapper.readValue(is, new TypeReference<>() {});

		for (RegionMappingDto rm : list) {
			String krName = rm.getKr().trim();
			String enName = rm.getEn().trim().toLowerCase();
			String zhName = rm.getZh().trim();

			krToKr.put(krName, krName);
			enToKr.put(enName, krName);
			zhToKr.put(zhName, krName);
		}
	}

	/**
	 * 전달받은 시도명(kr / en / zh 중 하나)으로 한국어 시도명 반환.
	 * 매칭이 안 되면 null 리턴.
	 */
	public String getKrSidoName(String input) {
		if (input == null || input.isBlank()) {
			return null;
		}
		String key = input.trim();
		// 1) 한글로 입력된 경우
		if (krToKr.containsKey(key)) {
			return krToKr.get(key);
		}
		// 2) 영어(소문자)로 입력된 경우
		String lower = key.toLowerCase();
		if (enToKr.containsKey(lower)) {
			return enToKr.get(lower);
		}
		// 3) 중국어로 입력된 경우
		if (zhToKr.containsKey(key)) {
			return zhToKr.get(key);
		}
		return null;
	}

	/** 전체 Region 목록 반환 (영어·한국어·중국어 모두 포함) */
	public List<RegionMappingDto> getAllRegions() throws Exception {
		// 이후에도 동일 JSON을 읽거나, 위 init()에서 리스트를 필드에 따로 저장해두고 반환해도 됩니다.
		ObjectMapper mapper = new ObjectMapper();
		InputStream is = new ClassPathResource("data/regions.json").getInputStream();
		return mapper.readValue(is, new TypeReference<>() {});
	}
}
