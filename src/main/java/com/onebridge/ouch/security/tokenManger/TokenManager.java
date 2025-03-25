package com.onebridge.ouch.security.tokenManger;

import org.springframework.security.core.Authentication;

import com.onebridge.ouch.security.authenticationToken.OuchAuthenticationToken;

public interface TokenManager {
	OuchAuthenticationToken readToken(String token);

	String writeToken(Authentication authentication);
}
