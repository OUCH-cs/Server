package com.hy.ouch.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hy.ouch.apiPayload.code.error.AuthorityErrorCode;
import com.hy.ouch.apiPayload.exception.OuchException;
import com.hy.ouch.security.authenticationToken.OuchAuthenticationToken;
import com.hy.ouch.security.service.OuchUserDetailService;
import com.hy.ouch.security.userDetail.OuchUserDetails;

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
				userDetails.getUsername(),
				userDetails.getPassword(),
				userDetails.getAuthorities(),
				((OuchUserDetails)userDetails).getDatabaseId());
		} else {
			throw new OuchException(AuthorityErrorCode.PASSWORD_NOT_MATCH);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return OuchAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
