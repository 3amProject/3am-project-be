package com.tam.threeam.service;

import com.tam.threeam.config.auth.PrincipalDetail;
import com.tam.threeam.model.User;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @author 이동은
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2021/12/30
 * @
 * @ 수정일         수정자                   수정내용
 * @ ———    ————    —————————————
 * @ 2021/12/30		    이동은        최초 작성
 * @ 2022/1/3			전예지		유저 정보 수정
 *
 */
public interface UserService {


    // 사용자 추가 / 회원가입
    @Transactional
    public Map<String, String> join(User user);


    // 주문자(유저) 정보
    @Transactional
    public User findUser(int userSeq);


    // 유저 아이디 중복 체크
    @Transactional
    public Map<String, String> checkUserId(String userId);

    
    // TODO 유저 정보 수정
    @Transactional
    public Map<String, String> updateProfile(User requestUser);
}
