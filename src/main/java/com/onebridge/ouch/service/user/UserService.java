package com.onebridge.ouch.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onebridge.ouch.apiPayload.code.error.UserErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;
import com.onebridge.ouch.converter.UserConverter;
import com.onebridge.ouch.domain.User;
import com.onebridge.ouch.domain.enums.UserStatus;
import com.onebridge.ouch.dto.user.response.UserInfoResponse;
import com.onebridge.ouch.repository.nation.NationRepository;
import com.onebridge.ouch.repository.user.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final NationRepository nationRepository;
	private final UserConverter userConverter;

	//유저 조회(테스트용)
	@Transactional(readOnly = true)
	public UserInfoResponse getUserInfo(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new OuchException(UserErrorCode.USER_NOT_FOUND));

		return userConverter.convertToUserInfoResponse(user);
	}

	//유저 탈퇴(비활성화)
	@Transactional
	public void deactivateUser(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new OuchException(UserErrorCode.USER_NOT_FOUND));

		User deactivatedUser = user.toBuilder()
			.status(UserStatus.INACTIVE)
			.build();

		userRepository.save(deactivatedUser);
	}

	//유저 삭제(테스트용)
	@Transactional
	public void deleteUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new OuchException(UserErrorCode.USER_NOT_FOUND));
		userRepository.delete(user);
	}
}






































