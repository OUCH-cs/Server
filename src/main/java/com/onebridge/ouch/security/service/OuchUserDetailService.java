package com.onebridge.ouch.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onebridge.ouch.apiPayload.code.error.CommonErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;
import com.onebridge.ouch.repository.user.UserRepository;
import com.onebridge.ouch.security.userDetail.OuchUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OuchUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		return userRepository.findByLoginId(username)
			.map(OuchUserDetails::new)
			.orElseThrow(() -> new OuchException(CommonErrorCode.RESOURCE_NOT_FOUND));
	}
}
