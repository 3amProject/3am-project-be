package com.tam.threeam.controller;

import com.tam.threeam.config.JwtTokenUtil;
import com.tam.threeam.config.auth.PrincipalDetail;
import com.tam.threeam.model.JwtRequest;
import com.tam.threeam.response.Exception.InvalidRefreshTokenException;
import com.tam.threeam.response.ResponseDto;
import com.tam.threeam.model.User;
import com.tam.threeam.service.CartService;
import com.tam.threeam.service.UserService;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 이동은
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2021/12/30
 * @
 * @ 수정일            수정자           수정내용
 * @ ———             ————           —————————————
 * @ 2021/12/30     	최초 작성
 * @ 2022/01/03		전예지	        유저 정보 조회/수정
 * @ 2022/01/12		전예지			유저 정보 조회 리턴 타입 수정
 * @ 2022/01/19      	이동은         validation ExceptionHandler로 처리
 * @ 2022/01/25		전예지			url 수정, 마이페이지 조회 수정
 * @ 2022/01/31		전예지			비회원 장바구니 로그인 후 회원 장바구니로 이동
 */
@Controller
public class UserController {

    @Autowired
    private UserService userServiceImpl;
    
    @Autowired
    private CartService cartServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Value("${security.jwt.token.secret-key}")
    private String secretKey;


    
/*  react로 대체
    // 회원가입 화면 조회
    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "joinForm";
    }
*/

    
    // 회원 가입 요청
    @ResponseBody
    @PostMapping("/auth/signUpProc")
    public ResponseDto signUp(@RequestBody User user) {

        return ResponseDto.sendMessage(userServiceImpl.join(user));
    }


    // 유저 아이디 중복 체크
    @ResponseBody
    @GetMapping("/auth/checkUserId")
    public ResponseDto checkUsername(@RequestParam("userId") String userId) {

        return ResponseDto.sendMessage(userServiceImpl.checkUserId(userId));
    }

    
    // 로그인 화면 조회
    @GetMapping("/auth/signInForm")
    public String signInForm() {
    	return "user/singInForm";

    }


    /* TODO 로그인 POST에 아래 로직 추가
     * @ 비회원 장바구니 로그인 후 회원 장바구니로 이동
     * */
    @PostMapping("로그인 post")
    public String shiftCart(HttpServletRequest request, @AuthenticationPrincipal PrincipalDetail principalDetail, HttpServletResponse response) {
    	// 요청값에서 "cartCookie"라는 key값의 쿠키 가져오기
    	Cookie cookie = WebUtils.getCookie(request, "cartCookie");
    	int userSeq = userServiceImpl.findUserPk(principalDetail.getUsername());
    	
    	if(cookie != null) {
    		String cookieValue = cookie.getValue();
    		// 쿠키에 담긴 정보에 userSeq 입력
    		cartServiceImpl.shiftCart(userSeq, cookieValue);
    		
    		// 쿠키 삭제
    		cookie.setPath("/");
    		cookie.setMaxAge(0);
    		response.addCookie(cookie);
    	}
    	return "로그인 후 회원 장바구니로 이동";
    }    
    

    @PostMapping("/auth/signInProc")
    public String signIn(@RequestBody JwtRequest jwtRequest , HttpServletResponse response) throws Exception {

        System.out.println(jwtRequest);
        System.out.println(jwtRequest.getUserId());
        System.out.println(jwtRequest.getPassword());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserId(), jwtRequest.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        String accessToken = "";
        String refreshToken = "";

        accessToken = jwtTokenUtil.generateToken(jwtRequest.getUserId() , 1);
        refreshToken = jwtTokenUtil.generateToken(jwtRequest.getUserId() , 3);
        Cookie refreshCookie = new Cookie("refreshToken" , refreshToken);
        refreshCookie.setMaxAge(3 * 60);
        response.addCookie(refreshCookie);

        return accessToken;
    }

    @PostMapping("/auth/refreshToken")
    public String refreshToken(@RequestBody JwtRequest jwtRequest , HttpServletRequest request) throws Exception{

        String accessToken = "";
        String refreshToken = "";

        Cookie [] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0 ) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                    if(jwtTokenUtil.checkClaim(refreshToken)) {
                        accessToken = jwtTokenUtil.generateToken(jwtRequest.getUserId() , 1);
                    }else {
                        throw new InvalidRefreshTokenException();
                    }
                }
            }
        }

        if(refreshToken == null || "".equals(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }


        return accessToken;
    }


    

    // TODO 마이페이지 조회
    @GetMapping("/user/myPage")
    public ResponseDto profileForm() {
    	return ResponseDto.sendData(userServiceImpl.myPage());

    }
    
    
    // TODO 유저 정보 수정 화면 url 주소 , sendData 로 보내기.
    @GetMapping("/user/profile/update")
    public String updateProfileForm() {

    	return "user/profile/update";
    }
    
    
    // 유저 정보 수정
    @PutMapping("/user/profile/update")
    public ResponseDto updateProfile(@RequestBody User user) {   	
    	return 	ResponseDto.sendMessage(userServiceImpl.updateProfile(user));
    }
}
