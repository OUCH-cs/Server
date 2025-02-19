package com.hy.ouch.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hy.ouch.converter.UserConverter;
import com.hy.ouch.domain.User;
import com.hy.ouch.domain.enums.UserStatus;
import com.hy.ouch.dto.user.response.UserInfoResponse;
import com.hy.ouch.repository.nation.NationRepository;
import com.hy.ouch.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final NationRepository nationRepository;
	private final UserConverter userConverter;

	@Transactional(readOnly = true)
	public UserInfoResponse getUserInfo(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

		return userConverter.user2UserInfoResponse(user);
	}

	@Transactional
	public void deactivateUser(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("User not found"));

		User deactivatedUser = user.toBuilder()
			.status(UserStatus.INACTIVE)
			.build();

		userRepository.save(deactivatedUser);
	}

	@Transactional
	public void deleteUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		userRepository.delete(user);
	}

	//test 요기
}






































