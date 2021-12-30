package com.tam.threeam.service;

import com.tam.threeam.model.User;
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
 * @ 2021/12/30     최초 작성
 */
public interface UserService {


    // 사용자 추가 / 회원가입
    @Transactional
    public Map<String, String> join(User user);


    // 회원 찾기
    @Transactional
    public User findUser(String userId);


    // 유저 아이디 중복 체크
    @Transactional
    public Map<String, String> checkUserId(String userId);

}
