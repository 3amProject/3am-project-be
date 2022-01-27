package com.tam.threeam.config.auth;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author 전예지
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2022/01/14
 * @
 * @ 수정일       수정자                   수정내용
 * @ ———    ————    —————————————
 * @ 2022/01/14     전예지       최초 작성
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable{

	private static final long serialVersionUID = -7773895444441291311L;
	
	/*
	 * @ 인증 필요한 resource 접근 시 && 예외 발생 시 호출
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized. You need to provide Jwt Token to access this source");
		
	}

	
	
	
}
