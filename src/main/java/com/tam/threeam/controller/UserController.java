package com.tam.threeam.controller;

import com.tam.threeam.config.auth.PrincipalDetail;
import com.tam.threeam.response.ResponseDto;
import com.tam.threeam.model.User;
import com.tam.threeam.service.UserService;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
 */
@Controller
public class UserController {

    @Autowired
    private UserService userServiceImpl;

    
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
    public ResponseDto join(@RequestBody User user) {

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
    public String loginForm() {
    	return "user/loginForm";
    }
    

    // 마이페이지 조회
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
