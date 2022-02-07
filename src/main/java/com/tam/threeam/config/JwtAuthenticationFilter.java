package com.tam.threeam.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tam.threeam.config.auth.PrincipalDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author 전예지
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2022/01/19
 * @
 * @ 수정일     	 	 수정자        수정내용
 * @ ———   			 ————    —————————————
 * @ 2022/01/19      전예지       최초 작성
 * @ 2022/01/27		 이동은
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private PrincipalDetailService principalDetailService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		final String requestTokenHeader = jwtTokenUtil.getRequestTokenHeader(request);
		log.info("requestTokenHeader : {}", requestTokenHeader);

		String userId = null;
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith(JwtTokenUtil.BEARER_TYPE)) {
			jwtToken = requestTokenHeader.substring(7);
			log.info("jwtToken : {}", jwtToken);

			if (jwtTokenUtil.validateToken(jwtToken)) {
				userId = jwtTokenUtil.getUserIdFromToken(jwtToken);
			}
		}

		if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails principalDetail = principalDetailService.loadUserByUsername(userId);

			log.info("principalDetail : {}", principalDetail);

			if (jwtTokenUtil.validateToken(jwtToken, principalDetail)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principalDetail, null, principalDetail.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);
	}
}
