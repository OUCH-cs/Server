package com.hy.ouch.security.converter;

import com.hy.ouch.security.authenticationToken.OuchAuthenticationToken;
import com.hy.ouch.security.dto.request.SignInRequest;

public class SignInConverter {

	public static OuchAuthenticationToken toAuthenticationToken(SignInRequest signInRequest) {
		return new OuchAuthenticationToken(signInRequest.getLoginId(), signInRequest.getPassword());
	}
}