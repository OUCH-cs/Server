package com.onebridge.ouch.service.language;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.onebridge.ouch.domain.Language;
import com.onebridge.ouch.repository.language.LanguageRepository;
import com.onebridge.ouch.dto.language.response.GetAllLanguagesResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LanguageService {

	private final LanguageRepository languageRepository;

	public List<GetAllLanguagesResponse> getAllLanguages() {
		List<Language> languages = languageRepository.findAll();
		return languages.stream()
			.map(language -> new GetAllLanguagesResponse(language.getName(), language.getCode()))
			.collect(Collectors.toList());
	}
}
