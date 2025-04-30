package com.onebridge.ouch.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.onebridge.ouch.apiPayload.code.error.AuthorityErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;
import com.onebridge.ouch.security.authenticationToken.OuchAuthenticationToken;
import com.onebridge.ouch.security.service.OuchUserDetailService;
import com.onebridge.ouch.security.userDetail.OuchUserDetails;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OuchUserAuthenticationProvider implements AuthenticationProvider {

	private final OuchUserDetailService ouchUserDetailService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String loginId = authentication.getName();
		String password = (String)authentication.getCredentials();

		UserDetails userDetails = ouchUserDetailService.loadUserByUsername(loginId);

		if (passwordEncoder.matches(password, userDetails.getPassword())) {
			return new OuchAuthenticationToken(
				userDetails,                       // principal은 UserDetails 객체, 기존에는 userDetails.getUsername()로 string으로 받음
				null,                              // 인증 후 credentials는 null, 기존에는 userDetails.getPassword()로 비밀번호 유지 인증 후 credentials는 null
				userDetails.getAuthorities(),
				((OuchUserDetails)userDetails).getDatabaseId()); // PK를 getDetails()로 받기 위해 따로 만든 메소드이므로 다운캐스팅
		} else {
			throw new OuchException(AuthorityErrorCode.PASSWORD_NOT_MATCH);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return OuchAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
