package com.tam.threeam.service;

import com.tam.threeam.mapper.UserMapper;
import com.tam.threeam.model.User;
import com.tam.threeam.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder encoder;


    // 회원가입
    @Override
    @Transactional
    public Map<String, String> join(User user) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("messageType", "success");
        resultMap.put("message", "회원가입이 완료되었습니다.");

        if(CommonUtils.isUserId(user.getName()) == false) {
            resultMap.put("messageType" , "failure");
            resultMap.put("message", "아이디는 3자 이상 12자 이하의 숫자, 영어 대/소문자로 입력해주세요.");

            return resultMap;
        }

        String rawPassword = user.getPassword();
        if(CommonUtils.isPassword(rawPassword)) {
            resultMap.put("messageType", "failure");
            resultMap.put("message", "비밀번호는 3자 이상 12자 이하의 숫자, 영어 대/소문자로 입력해주세요.");

            return resultMap;
        }
        String encPassword = encoder.encode(rawPassword);
        user.setPassword(encPassword);

        if(CommonUtils.isNotEmpty(user.getEmail()) == false) {
            resultMap.put("messageType", "failure");
            resultMap.put("message", "이메일을 형식에 맞게 입력해주세요.");

            return resultMap;
        }

        userMapper.join(user);
        return resultMap;



        return  resultMap;

    };


    // 회원 찾기
    @Override
    @Transactional
    public User findUser(String userId) {
        User user = userMapper.findByUsername(username).orElseGet(() -> {
            return new User();
        });

        return user;

    };


    // 유저 아이디 중복 체크
    @Override
    @Transactional
    public Map<String, String> checkUsername(String username) {
        int count = userMapper.checkUsername(username);

        Map<String, String> resultMap = new HashMap<>();

        resultMap.put("messageType", count == 0 ? "success" : "failure");
        resultMap.put("message", count == 0 ? "사용하실 수 있는 아이디입니다." : username+"은 이미 있는 아이디입니다.");
        return resultMap;
    }


}
