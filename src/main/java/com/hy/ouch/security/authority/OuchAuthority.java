package com.hy.ouch.security.authority;

import org.springframework.security.core.GrantedAuthority;

public enum OuchAuthority implements GrantedAuthority {
	INDIVIDUAL, HOSPITAL;

	@Override
	public String getAuthority() {
		return name();
	}
}
