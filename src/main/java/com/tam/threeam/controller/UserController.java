package com.tam.threeam.controller;

import com.tam.threeam.config.auth.PrincipalDetail;
import com.tam.threeam.response.ResponseDto;
import com.tam.threeam.model.User;
import com.tam.threeam.service.UserService;

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
 * @ 수정일         수정자                   수정내용
 * @ ———    ————    —————————————
 * @ 2021/12/30     최초 작성
 * @ 2022/1/3			전예지		유저 정보 조회/수정		
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
    @PostMapping("/auth/joinProc")
    public ResponseDto join(@RequestBody User user) { //throws ApiException
        int result = 0;
        result = userServiceImpl.join(user);

        return ResponseDto.sendSuccess(result);
    }



    // 유저 아이디 중복 체크
    @ResponseBody
    @GetMapping("/auth/checkUserId")
    public ResponseDto checkUsername(@RequestParam("userId") String userId) {

        return ResponseDto.sendData(userServiceImpl.checkUserId(userId));
    }

    // TODO 하단 API들 return 타입 수정 : ResponseDto.sendData
    
    
    // 로그인 화면 조회
    @GetMapping("/auth/loginForm")
    public String loginForm() {
    	return "user/loginForm";
    }
    

    // 유저 정보 조회
    @GetMapping("/user/profile")
    public String profileForm(Model model, @AuthenticationPrincipal PrincipalDetail principalDetail) {
    	User user = new User();

    	user.setName(principalDetail.getName());
    	user.setUserId(principalDetail.getUsername());
    	user.setPhoneNum(principalDetail.getPhoneNum());
    	user.setAddress(principalDetail.getAddress());
    	user.setEmail(principalDetail.getEmail());

    	model.addAttribute("userProfile", user);

    	return "user/profile";
    }
    
    
    // 유저 정보 수정 화면
    @GetMapping("/user/updateProfileForm")
    public String updateProfileForm() {
    	return "user/profile/update";
    }
    
    
    // TODO 유저 정보 수정
    @PutMapping("/user/profile/update")
    public ResponseDto updateProfile(@RequestBody User user) {   	
    	return 	ResponseDto.sendData(userServiceImpl.updateProfile(user));
    }
}
