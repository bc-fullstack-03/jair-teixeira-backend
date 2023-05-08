package com.sysmap.parrot.services.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService implements IJwtService{
	@Value("${application.security.jwt.secret-key}")
	private String secretKey;
	@Value("${application.security.jwt.expiration}")
	private long jwtExpiration;
	@Value("${application.security.jwt.refresh-token.expiration}")
	private long refreshExpiration;
	private String buildToken( Map<String, Object> extraClaims,
			                   UserDetails userDetails,
			                   long expiration) {

		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	public String generateToken(
			Map<String, Object> extraClaims,
			UserDetails userDetails
	) {
		return buildToken(extraClaims, userDetails, jwtExpiration);
	}
	public String generateRefreshToken(UserDetails userDetails) {
		return buildToken(new HashMap<>(), userDetails, refreshExpiration);
	}
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	private boolean isTokenExpired(String token) {
		var expiration = extractClaim(token, Claims::getExpiration);
		return expiration.before(new Date());
	}
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = Jwts.parserBuilder()
								.setSigningKey(getSignInKey())
								.build()
								.parseClaimsJws(token)
								.getBody();
		return claimsResolver.apply(claims);
	}
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	/*public boolean isValidToken(String token, String userId) {
		Claims claims = Jwts.parserBuilder().setSigningKey(getSignInKey())
				.build().parseClaimsJws(token).getBody();
		String subject = claims.getSubject();
		Date timeExpiration = claims.getExpiration();

		return subject.equals(userId) && timeExpiration.after(new Date());
	}*/
	/*public String generateToken(UUID userId) {
		return Jwts
				.builder()
				.setSubject(userId.toString())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}*/
	/*private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}*/
	/*private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}*/
}
