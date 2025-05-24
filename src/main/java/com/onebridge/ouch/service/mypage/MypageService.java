package com.onebridge.ouch.service.mypage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onebridge.ouch.apiPayload.code.error.CommonErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;
import com.onebridge.ouch.converter.MypageConverter;
import com.onebridge.ouch.domain.Language;
import com.onebridge.ouch.domain.Nation;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.dto.mypage.request.MypageProfileUpdateRequest;
import com.onebridge.ouch.dto.mypage.response.MypageGetProfileResponse;
import com.onebridge.ouch.repository.language.LanguageRepository;
import com.onebridge.ouch.repository.nation.NationRepository;
import com.onebridge.ouch.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageService {

	private final UserRepository userRepository;
	private final NationRepository nationRepository;
	private final MypageConverter mypageConverter;
	private final LanguageRepository languageRepository;

	@Transactional(readOnly = true)
	public MypageGetProfileResponse mypageGetProfile(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new OuchException(CommonErrorCode.MEMBER_NOT_FOUND));
		return mypageConverter.userToMypageGetProfileResponse(user);
	}

	@Transactional
	public void mypageUpdateProfile(Long userId, MypageProfileUpdateRequest request) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new OuchException(CommonErrorCode.MEMBER_NOT_FOUND));

		Nation nation = nationRepository.findByCode(request.getNationCode())
			.orElseThrow(() -> new OuchException(CommonErrorCode.NATION_NOT_FOUND));

		// Language language = languageRepository.findByCode(request.getLanguageCode())
		// 	.orElseThrow(() -> new OuchException(CommonErrorCode.LANGUAGE_NOT_FOUND));

		User updatedUser = mypageConverter.updateUserByUpdateProfileRequest(user, request, nation);
		userRepository.save(updatedUser);
	}

}
