package com.hy.ouch.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hy.ouch.apiPayload.code.BaseErrorCode;
import com.hy.ouch.apiPayload.code.status.ErrorStatus;
import com.hy.ouch.apiPayload.exception.GeneralException;
import com.hy.ouch.domain.Language;
import com.hy.ouch.repository.LanguageRepository;
import com.hy.ouch.web.dto.LanguageDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LanguageService {

	private final LanguageRepository languageRepository;

	public Long addLanguage(LanguageDto languageDto) {
		Language language = Language.builder()
			.name(languageDto.getName())
			.code(languageDto.getCode())
			.build();
		Language savedLanguage = languageRepository.save(language);
		return savedLanguage.getId();
	}

	public void updateLanguage(Long id, LanguageDto languageDto) {
		Language existingLanguage = languageRepository.findById(id)
			.orElseThrow(() -> new GeneralException(ErrorStatus.RESOURCE_NOT_FOUND));

		existingLanguage.updateLanguageFields(
			languageDto.getName(),
			languageDto.getCode()
		);
		languageRepository.save(existingLanguage);
	}

	public void deleteLanguage(Long id) {
		if (!languageRepository.existsById(id)) {
			throw new GeneralException(ErrorStatus.RESOURCE_NOT_FOUND);
		}
		languageRepository.deleteById(id);
	}

	public List<LanguageDto> getAllLanguages() {
		List<Language> languages = languageRepository.findAll();
		return languages.stream()
			.map(language -> new LanguageDto(language.getName(), language.getCode()))
			.collect(Collectors.toList());
	}

}
