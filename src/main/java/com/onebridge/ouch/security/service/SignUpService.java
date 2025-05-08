package com.onebridge.ouch.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.onebridge.ouch.apiPayload.code.error.CommonErrorCode;
import com.onebridge.ouch.apiPayload.code.error.SecurityErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;
import com.onebridge.ouch.converter.HealthStatusConverter;
import com.onebridge.ouch.domain.HealthStatus;
import com.onebridge.ouch.domain.Language;
import com.onebridge.ouch.domain.Nation;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.repository.healthStatus.HealthStatusRepository;
import com.onebridge.ouch.repository.language.LanguageRepository;
import com.onebridge.ouch.repository.nation.NationRepository;
import com.onebridge.ouch.repository.user.UserRepository;
import com.onebridge.ouch.security.dto.request.SignUpRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class SignUpService {

	private final UserRepository userRepository;
	public final PasswordEncoder passwordEncoder;
	private final LanguageRepository languageRepository;
	private final NationRepository nationRepository;
	private final HealthStatusConverter healthStatusConverter;
	private final HealthStatusRepository healthStatusRepository;

	public void signUpUser(SignUpRequest signUpRequest) {
		checkDuplicatedLoginId(signUpRequest.getLoginId());
		checkDuplicatedNickname(signUpRequest.getNickname());

		// ID를 통해 실제 Entity 조회
		Language language = languageRepository.findByCode(signUpRequest.getLanguageCode())
			.orElseThrow(() -> new OuchException(CommonErrorCode.LANGUAGE_NOT_FOUND));

		Nation nation = nationRepository.findByCode(signUpRequest.getNationCode())
			.orElseThrow(() -> new OuchException(CommonErrorCode.NATION_NOT_FOUND));

		User user = signUpRequest.toEntity(passwordEncoder.encode(signUpRequest.getPassword()), language, nation);
		userRepository.save(user);

		HealthStatus healthStatus = healthStatusConverter.createHealthStatus(user);
		healthStatusRepository.save(healthStatus);
	}

	public void checkDuplicatedLoginId(String loginId) {
		if (userRepository.findByLoginId(loginId).isPresent()) {
			throw new OuchException(SecurityErrorCode.IDENTIFIER_DUPLICATED);
		}
	}

	public void checkDuplicatedNickname(String nickname) {
		if (userRepository.findByNickname(nickname).isPresent()) {
			throw new OuchException(SecurityErrorCode.NICKNAME_DUPLICATED);
		}
	}
}
