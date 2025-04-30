package com.onebridge.ouch.service.nation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onebridge.ouch.domain.Nation;
import com.onebridge.ouch.dto.nation.response.GetNationResponse;
import com.onebridge.ouch.repository.nation.NationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NationService {

	private final NationRepository nationRepository;

	@Transactional
	public List<GetNationResponse> getAllNations() {
		List<Nation> nations = nationRepository.findAll();
		return nations.stream()
			.map(nation -> new GetNationResponse(nation.getName(), nation.getCode()))
			.collect(Collectors.toList());
	}
}
