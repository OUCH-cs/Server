package com.onebridge.ouch.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

import com.onebridge.ouch.security.provider.OuchUserAuthenticationProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AuthenticationManagerConfig {

	private final OuchUserAuthenticationProvider ouchUserAuthenticationProvider;

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		List<AuthenticationProvider> providerList = new ArrayList<>();
		providerList.add(ouchUserAuthenticationProvider);
		return new ProviderManager(providerList);
	}
}

