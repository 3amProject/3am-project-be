package com.tam.threeam.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author 전예지
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2022/01/19
 * @
 * @ 수정일       	수정자       수정내용
 * @ ———    		———— 	   —————————————
 * @ 2022/01/19     전예지       최초 작성
 * @ 2022/01/27		이동은	   유틸 추가
 */
@Slf4j
@Component
public class JwtTokenUtil implements Serializable{

	private static final long serialVersionUID = -3087900894366041265L;
	public static final long tokenValidTime = 60 * 1000;

	@Value("${security.jwt.token.secret-key}")
	private String secretKey;



	public String getUserIdFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}


	public String generateToken(String userId, long expirationMinute) {

		String token = "";

		Map<String, Object> claims = new HashMap<>();
		String subject = userId;

		token = Jwts.builder()
						.setClaims(claims)
						.setSubject(subject)
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis() + tokenValidTime * expirationMinute))
						.signWith(SignatureAlgorithm.HS512, secretKey).compact();


		return token;

	}


	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userId = getUserIdFromToken(token);
		return (userId.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public boolean checkClaim(String token) {
		try {
			getAllClaimsFromToken(token);

			return true;

		}catch(ExpiredJwtException e) {
			log.error("Token Expired");

			return false;

		}catch(JwtException e) {
			log.error("Token Error" , e);

			return false;
		}
	}

//	public boolean checkClaim(String jwt) {
//		try {
//			Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
//					.parseClaimsJws(jwt).getBody();
//			return true;




}
