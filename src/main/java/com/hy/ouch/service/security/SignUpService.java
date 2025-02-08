package com.hy.ouch.service.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.hy.ouch.apiPayload.code.error.CommonErrorCode;
import com.hy.ouch.apiPayload.code.error.SecurityErrorCode;
import com.hy.ouch.apiPayload.exception.OuchException;
import com.hy.ouch.domain.Language;
import com.hy.ouch.domain.Nation;
import com.hy.ouch.domain.User;
import com.hy.ouch.dto.user.request.SignUpRequest;
import com.hy.ouch.repository.language.LanguageRepository;
import com.hy.ouch.repository.nation.NationRepository;
import com.hy.ouch.repository.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public abstract class SignUpService {

	private final UserRepository userRepository;
	public final PasswordEncoder passwordEncoder;
	private final LanguageRepository languageRepository;
	private final NationRepository nationRepository;

	public void signUpUser(SignUpRequest signUpRequest) {
		if (isDuplicated(signUpRequest.getLoginId())) {
			throw new OuchException(SecurityErrorCode.IDENTIFIER_DUPLICATED);
		}
		if (isDuplicatedNickName(signUpRequest.getNickname())) {
			throw new OuchException(SecurityErrorCode.NICKNAME_DUPLICATED);
		}

		// ID를 통해 실제 Entity 조회
		Language language = languageRepository.findById(signUpRequest.getLanguageId())
			.orElseThrow(() -> new OuchException(CommonErrorCode.LANGUAGE_NOT_FOUND));

		Nation nation = nationRepository.findById(signUpRequest.getNationId())
			.orElseThrow(() -> new OuchException(CommonErrorCode.NATION_NOT_FOUND));

		User user = signUpRequest.toEntity(passwordEncoder.encode(signUpRequest.getPassword()), language, nation);
		userRepository.save(user);
	}

	public boolean isDuplicated(String loginId) {
		return userRepository.findByLoginId(loginId).isPresent();
	}

	public boolean isDuplicatedNickName(String nickname) {
		return userRepository.findByNickname(nickname).isPresent();
	}
}
