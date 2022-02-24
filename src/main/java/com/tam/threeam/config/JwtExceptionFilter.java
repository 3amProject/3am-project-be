package com.tam.threeam.config;

import com.tam.threeam.response.BaseResponseDTO;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @author 이동은
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2022/02/09
 * @
 * @ 수정일       	수정자        수정내용
 * @ ———    		————    	—————————————
 * @ 2021/02/09     이동은      	JwtAuthenticationFilter exception 처리해주는 filter 생성
 */
@Slf4j
@Component
public class JwtExceptionFilter  { //extends OncePerRequestFilter

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            filterChain.doFilter(request, response);
//        } catch (ExpiredJwtException e) {
//            log.error ("Expired JWT Token", e);
//            setResponse(response, "accessToken 만료", "ER101");
//        } catch (MalformedJwtException e) {
//			log.error("Invalid JWT Token", e);
//            setResponse(response, "유효하지 않은 토큰", "ER102");
//		} catch (UnsupportedJwtException e) {
//			log.info("Unsupported JWT Token", e);
//            setResponse(response, "지원하지 않는 토큰", "ER103");
//		} catch (IllegalArgumentException e) {
//			log.info("JWT claims string is empty.", e);
//            setResponse(response, "claim 정보가 없음", "ER104");
//		} catch (SignatureException e) {
//            log.info("Username or Password not valid.", e);
//            setResponse(response, "claim 정보가 없음", "ER105");
//        }
//
//    }
//
//
//    private void setResponse(HttpServletResponse response, String message, String code) throws IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//        JSONObject responseJson = new JSONObject();
//        responseJson.put("messageType", "FAIL");
//        responseJson.put("message", message);
//        responseJson.put("code", code);
//
//        response.getWriter().print(responseJson);
//    }

}
