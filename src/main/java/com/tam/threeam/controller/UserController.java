package com.tam.threeam.controller;

import com.tam.threeam.dto.ResponseDto;
import com.tam.threeam.model.User;
import com.tam.threeam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 */
@Controller
public class UserController {

    @Autowired
    private UserService userServiceImpl;

    // 회원가입 화면 조회
    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }


    // 회원 가입 요청
    @ResponseBody
    @PostMapping("/auth/joinProc")
    public ResponseDto join(@RequestBody User user) {

        return 	ResponseDto.sendData(userServiceImpl.join(user));
    }


    // 유저 아이디 중복 체크
    @ResponseBody
    @GetMapping("/auth/checkUsername")
    public ResponseDto checkUsername(@RequestParam("username") String username) {

        return ResponseDto.sendData(userServiceImpl.checkUserId(username));
    }

}
