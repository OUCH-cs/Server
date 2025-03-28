package com.onebridge.ouch.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.onebridge.ouch.apiPayload.code.error.CommonErrorCode;
import com.onebridge.ouch.apiPayload.code.error.SecurityErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;
import com.onebridge.ouch.domain.Language;
import com.onebridge.ouch.domain.Nation;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.security.dto.request.SignUpRequest;
import com.onebridge.ouch.repository.language.LanguageRepository;
import com.onebridge.ouch.repository.nation.NationRepository;
import com.onebridge.ouch.repository.user.UserRepository;

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

	public void signUpUser(SignUpRequest signUpRequest) {
		checkDuplicatedLoginId(signUpRequest.getLoginId());
		checkDuplicatedNickname(signUpRequest.getNickname());

		// ID를 통해 실제 Entity 조회
		Language language = languageRepository.findById(signUpRequest.getLanguageId())
			.orElseThrow(() -> new OuchException(CommonErrorCode.LANGUAGE_NOT_FOUND));

		Nation nation = nationRepository.findById(signUpRequest.getNationId())
			.orElseThrow(() -> new OuchException(CommonErrorCode.NATION_NOT_FOUND));

		User user = signUpRequest.toEntity(passwordEncoder.encode(signUpRequest.getPassword()), language, nation);
		userRepository.save(user);
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
