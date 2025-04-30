package com.onebridge.ouch.service.language;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onebridge.ouch.domain.Language;
import com.onebridge.ouch.repository.language.LanguageRepository;
import com.onebridge.ouch.dto.language.response.GetLanguageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LanguageService {

	private final LanguageRepository languageRepository;

	@Transactional
	public List<GetLanguageResponse> getAllLanguages() {
		List<Language> languages = languageRepository.findAll();
		return languages.stream()
			.map(language -> new GetLanguageResponse(language.getName(), language.getCode()))
			.collect(Collectors.toList());
	}
}
