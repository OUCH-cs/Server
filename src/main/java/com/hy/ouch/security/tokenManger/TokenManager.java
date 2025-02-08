package com.hy.ouch.security.tokenManger;

import org.springframework.security.core.Authentication;

import com.hy.ouch.security.authenticationToken.OuchAuthenticationToken;

public interface TokenManager {
	OuchAuthenticationToken readToken(String token);

	String writeToken(Authentication authentication);
}
