package com.tam.threeam.service;

import com.tam.threeam.mapper.UserMapper;
import com.tam.threeam.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * @ 2021/12/30		이동은     최초 작성
 * @ 2022/1/3			전예지		유저 정보 수정
 */
@Service
public class UserServiceImpl {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder encoder;


    // 사용자 추가 / 회원가입
    @Override
    @Transactional
    public Map<String, String> join(User user) {


    };


    // 회원 찾기
    @Override
    @Transactional
    public User findUser(String userId) {


    };


    // 유저 아이디 중복 체크
    @Override
    @Transactional
    public Map<String, String> checkUserId(String userId) {


    };
    
    // TODO 유저 정보 수정 => 세션에서도 수정되도록
    @Override
    @Transactional
    public Map<String, String> updateProfile(User user) {
    	userMapper.updateUserInfo(user);
    	
    }

}
