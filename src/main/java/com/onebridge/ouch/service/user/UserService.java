package com.onebridge.ouch.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onebridge.ouch.apiPayload.code.error.CommonErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;
import com.onebridge.ouch.converter.UserConverter;
import com.onebridge.ouch.domain.Language;
import com.onebridge.ouch.domain.Nation;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.domain.enums.UserStatus;
import com.onebridge.ouch.dto.language.response.GetLanguageResponse;
import com.onebridge.ouch.dto.nation.response.GetNationResponse;
import com.onebridge.ouch.dto.user.response.UserInfoResponse;
import com.onebridge.ouch.repository.language.LanguageRepository;
import com.onebridge.ouch.repository.nation.NationRepository;
import com.onebridge.ouch.repository.user.UserRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final UserConverter userConverter;
	private final NationRepository nationRepository;
	private final LanguageRepository languageRepository;

	//유저 조회(테스트용)
	@Transactional(readOnly = true)
	public UserInfoResponse getUserInfo(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new OuchException(CommonErrorCode.MEMBER_NOT_FOUND));

		return userConverter.convertToUserInfoResponse(user);
	}

	//유저 탈퇴(비활성화)
	@Transactional
	public void deactivateUser(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new OuchException(CommonErrorCode.MEMBER_NOT_FOUND));

		user.deactivate();
	}

	@Transactional
	public void updateUserLanguage(Long userId, String languageCode) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new OuchException(CommonErrorCode.MEMBER_NOT_FOUND));

		Language language = languageRepository.findByCode(languageCode)
			.orElseThrow(() -> new OuchException(CommonErrorCode.LANGUAGE_NOT_FOUND));

		user.updateLanguage(language);
	}

	@Transactional
	public GetLanguageResponse getUserLanguage(Long userId) {
		User user = userRepository.findWithLanguageById(userId)
			.orElseThrow(() -> new OuchException(CommonErrorCode.MEMBER_NOT_FOUND));

		Language language = user.getLanguage();

		return new GetLanguageResponse(language.getName(), language.getCode());
	}

	@Transactional
	public void updateUserNation(Long userId, String nationCode) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new OuchException(CommonErrorCode.MEMBER_NOT_FOUND));

		Nation nation = nationRepository.findByCode(nationCode)
			.orElseThrow(() -> new OuchException(CommonErrorCode.NATION_NOT_FOUND));

		user.updateNation(nation);
	}

	@Transactional
	public GetNationResponse getUserNation(Long userId) {
		User user = userRepository.findWithNationById(userId)
			.orElseThrow(() -> new OuchException(CommonErrorCode.MEMBER_NOT_FOUND));

		Nation nation = user.getNation();

		return new GetNationResponse(nation.getName(), nation.getCode());
	}
}
