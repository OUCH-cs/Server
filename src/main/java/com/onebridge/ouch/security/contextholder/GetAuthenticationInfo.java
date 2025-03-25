package com.onebridge.ouch.security.contextholder;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.onebridge.ouch.apiPayload.code.error.AuthorityErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;
import com.onebridge.ouch.security.authority.OuchAuthority;

public class GetAuthenticationInfo {

	public static Long getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (Long)authentication.getDetails();
	}

	public static OuchAuthority getAuthority() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getAuthorities().stream()
			.map(OuchAuthority.class::cast)
			.findFirst()
			.orElseThrow(() -> new OuchException(AuthorityErrorCode.AUTHORITY_ERROR_CODE));
	}
}
