package com.onebridge.ouch.security.converter;

import com.onebridge.ouch.security.authenticationToken.OuchAuthenticationToken;
import com.onebridge.ouch.security.dto.request.SignInRequest;

public class SignInConverter {

	public static OuchAuthenticationToken toAuthenticationToken(SignInRequest signInRequest) {
		return new OuchAuthenticationToken(signInRequest.getLoginId(), signInRequest.getPassword());
	}
}