package com.tam.threeam.controller;

import com.tam.threeam.config.JwtTokenUtil;
import com.tam.threeam.config.auth.PrincipalDetail;
import com.tam.threeam.model.JwtRequest;
import com.tam.threeam.response.Exception.InvalidRefreshTokenException;
import com.tam.threeam.response.ResponseDto;
import com.tam.threeam.model.User;
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
<<<<<<< HEAD
 * @ 2021/12/30     최초 작성
 * @ 2022/1/3		전예지	        유저 정보 조회/수정
 * @ 2022/1/3		전예지	    	유저 정보 조회/수정
 * @ 2022/1/12		전예지	    	유저 정보 조회 리턴 타입 수정
 * @ 2022/1/19      이동은            validation ExceptionHandler로 처리
=======
 * @ 2021/12/30     	최초 작성
 * @ 2022/01/03		전예지	        유저 정보 조회/수정
 * @ 2022/01/12		전예지			유저 정보 조회 리턴 타입 수정
 * @ 2022/01/19      	이동은         validation ExceptionHandler로 처리
 * @ 2022/01/25		전예지			url 수정, 마이페이지 조회 수정
>>>>>>> fetch_head
 */
@Controller
public class UserController {

    @Autowired
    private UserService userServiceImpl;

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




//    @PostMapping(value="/login", produces="application/json; charset=UTF-8")
//    public String signIn(@RequestParam HashMap<String, String> requestMap ,HttpServletResponse response) throws Exception {
//
//        JwtRequest jwtRequest = new JwtRequest();
//        jwtRequest.setUserId(requestMap.get("userId"));
//        jwtRequest.setPassword(requestMap.get("password"));
        @PostMapping("/login")
        public String signIn(@RequestBody JwtRequest jwtRequest ,HttpServletResponse response) throws Exception {

        System.out.println(jwtRequest);
        System.out.println(jwtRequest.getUserId());
        System.out.println(jwtRequest.getPassword());

        // 스프링 시큐리티 인증(authentication)
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserId(), jwtRequest.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        //TODO Jwt Token (access token, refresh token 각각 발급) -> 둘다 쿠키에 담아서 전송
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
    public String refreshToken(@RequestParam JwtRequest jwtRequest , HttpServletRequest request) throws Exception{

//        JwtRequest jwtRequest = new JwtRequest();
//        jwtRequest.setUserId(requestMap.get("userId"));
//        jwtRequest.setPassword(requestMap.get("password"));

        String accessToken = "";
        String refreshToken = "";

        //TODO RefreshToken 유효기간 만료 시 재발급
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
    public ResponseDto profileForm(@AuthenticationPrincipal PrincipalDetail principalDetail) {
    	User user = new User();
    	user.setName(principalDetail.getName());
    	user.setUserId(principalDetail.getUsername());
    	user.setPhoneNum(principalDetail.getPhoneNum());
    	user.setAddress(principalDetail.getAddress());
    	user.setEmail(principalDetail.getEmail());

    	Map<String, Object> resultMap = new HashMap<>();
    	resultMap.put("userProfile", user);

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
