package com.hy.ouch.security.tokenManger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.hy.ouch.security.authenticationToken.OuchAuthenticationToken;
import com.hy.ouch.security.authority.OuchAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtTokenManager implements TokenManager {

	private final String secretKey;

	public JwtTokenManager(@Value("${jwt.secret}") String secretKey) {
		this.secretKey = secretKey;
	}

	@Override
	public OuchAuthenticationToken readToken(String token) throws
		SignatureException, UnsupportedJwtException, MalformedJwtException, ExpiredJwtException {

		Claims claims = Jwts.parser()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody();

		String loginId = claims.get("loginId", String.class); // 로그인 시 사용하는 id값
		Long id = claims.get("id", Long.class); // 데이터베이스에서 사용되는 유저의 id값
		List<String> authorities = claims.get("authorities", List.class);

		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		OuchAuthenticationToken authenticationToken = null;

		if (authorities.get(0).equals(OuchAuthority.INDIVIDUAL.name())) {
			grantedAuthorities.add(OuchAuthority.INDIVIDUAL);
			authenticationToken = new OuchAuthenticationToken(loginId, null,
				grantedAuthorities,
				id);
		} else if (authorities.get(0).equals(OuchAuthority.HOSPITAL.name())) {
			grantedAuthorities.add(OuchAuthority.HOSPITAL);
			authenticationToken = new OuchAuthenticationToken(loginId, null,
				grantedAuthorities,
				id);
		} else {
			throw new MalformedJwtException("Ouch에 등록되지 않은 사용자입니다.");
		}
		return authenticationToken;
	}

	@Override
	public String writeToken(Authentication authentication) {
		return Jwts.builder()
			.setHeader(Map.of(
				"provider", "ouch",
				"type", "accessToken"
			))
			.setClaims(Map.of(
				"loginId", authentication.getPrincipal(),
				"authorities", authentication.getAuthorities(),
				"id", authentication.getDetails()
			))
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // ms 단위, 24시간
			.signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
			.compact();
	}
}
