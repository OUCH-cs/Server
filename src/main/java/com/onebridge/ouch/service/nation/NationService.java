package com.onebridge.ouch.service.nation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.onebridge.ouch.domain.Nation;
import com.onebridge.ouch.dto.nation.response.GetAllNationsResponse;
import com.onebridge.ouch.repository.nation.NationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NationService {

	private final NationRepository nationRepository;

	public List<GetAllNationsResponse> getAllNations() {
		List<Nation> nations = nationRepository.findAll();
		return nations.stream()
			.map(nation -> new GetAllNationsResponse(nation.getName(), nation.getCode()))
			.collect(Collectors.toList());
	}
}
