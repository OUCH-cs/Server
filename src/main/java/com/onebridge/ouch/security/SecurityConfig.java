package com.onebridge.ouch.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.onebridge.ouch.security.filter.JwtAuthenticationFilter;
import com.onebridge.ouch.security.tokenManger.TokenManager;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	private final TokenManager tokenManager;

	@Bean
	public SecurityFilterChain publicResourceConfig(HttpSecurity http) throws Exception {
		http.formLogin(FormLoginConfigurer::disable);
		http.csrf(AbstractHttpConfigurer::disable);
		http.cors(
			cors -> cors.configurationSource(corsConfigurationSource())
		);
		http.addFilterAt(new JwtAuthenticationFilter(tokenManager), BasicAuthenticationFilter.class);
		http.authorizeHttpRequests(
			(authorizeRequests)
				-> authorizeRequests.anyRequest().permitAll() // 모든 사용자 접근 가능
			//	-> authorizeRequests.anyRequest().authenticated() // 로그인한 사용자만 접근 가능
		);
		return http.build();

		/*
		http.authorizeHttpRequests(authorizeRequests ->
			authorizeRequests
				.antMatchers("/login", "/signup").permitAll()  // 로그인, 회원가입 페이지는 모두 허용
				.anyRequest().authenticated()                    // 그 외의 요청은 인증 필요
		);
		*/
	}

	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.addAllowedOriginPattern("*");
		//configuration.addAllowedOrigin("http://localhost:3000");
		//configuration.addAllowedOrigin("https://www.brainpix.net");

		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");

		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
