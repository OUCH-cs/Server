package com.onebridge.ouch.security;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
import org.springframework.web.filter.CorsFilter;

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
		http.authorizeHttpRequests(
			(authorizeRequests)
				-> authorizeRequests.requestMatchers("/users/login", "/users/signup/**", "/actuator/health", "/health",
					"/swagger-ui/**", "/v3/api-docs/**").permitAll() // 로그인, 회원가입 페이지는 모두 허용
				.anyRequest().authenticated() // 그 외의 요청은 인증 필요
		);
		http.addFilterAfter(new JwtAuthenticationFilter(tokenManager), BasicAuthenticationFilter.class);
		return http.build();
	}

	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.addAllowedOriginPattern("*");
		// configuration.addAllowedOrigin("http://localhost:5173");
		// configuration.addAllowedOrigin("https://ouchs.netlify.app");

		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");

		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	// CORS 필터 우선순위 추가
	// http.cors() 설정은 Spring Security가 인식할 수 있도록 등록하는 용도
	// 그런데 Spring Security의 인증 필터가 우선 적용돼서 OPTIONS 요청이 필터에서 차단되는 경우가 있음
	// 그래서 별도로 FilterRegistrationBean<CorsFilter>를 추가하면 이 필터가 모든 요청에서 가장 우선 실행되어서 확실히 적용됨
	/*
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(List.of(
			"http://localhost:5173",
			"https://ouchs.netlify.app"
		));
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(0); // 필터 최우선 순위 설정
		return bean;
	}
	*/
}
