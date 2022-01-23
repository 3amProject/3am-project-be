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
 * @ 2021/12/30     최초 작성
<<<<<<< HEAD
 * @ 2022/1/3		전예지	        유저 정보 조회/수정
 * @ 2022/1/19      이동은            validation ExceptionHandler로 처리
=======
 * @ 2022/1/3			전예지		유저 정보 조회/수정
 * @ 2022/1/12			전예지		유저 정보 조회 리턴 타입 수정
>>>>>>> fetch_head
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
    	
    	return ResponseDto.sendData(resultMap);

    }
    
    
    // TODO 유저 정보 수정 화면 url 주소 , sendData 로 보내기.
    @GetMapping("/user/profile/update/{userSeq}")
    public String updateProfileForm(@PathVariable("userSeq") int userSeq) {
    	return "user/profile/update";
    }
    
    
    // 유저 정보 수정
    @PutMapping("/user/profile/update")
    public ResponseDto updateProfile(@RequestBody User user) {   	
    	return 	ResponseDto.sendMessage(userServiceImpl.updateProfile(user));
    }
}
