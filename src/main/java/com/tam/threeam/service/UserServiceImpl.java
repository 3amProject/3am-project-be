package com.tam.threeam.service;

import com.tam.threeam.mapper.UserMapper;
import com.tam.threeam.model.User;
import com.tam.threeam.response.ApiException;
import com.tam.threeam.response.ExceptionEnum;
import com.tam.threeam.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author 이동은
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2021/12/30
 * @
 * @ 수정일            수정자          수정내용
 * @ ———             ————       —————————————
 * @ 2021/12/30	     이동은       최초 작성
 * @ 2022/1/3  		 전예지		유저 정보 수정
 * @ 2022/1/4		 이동은		회원가입 로직 완료
 * @ 2022/1/4		 전예지		유저 정보 수정 세션 반영
 * @ 2022/1/7        이동은       전화번호 양식 validation check 로직 추가
 * @ 2022/1/19       이동은       validation ExceptionHandler로 처리
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    
    // 회원가입
    @Override
    @Transactional
    public int join(User requestUser) throws ApiException {

        if(CommonUtils.isNotEmpty(requestUser.getUserId()) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_06);
        }
        if (userMapper.checkUserId(requestUser.getUserId()) != 0) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_01);
        }
        if(CommonUtils.isUserId(requestUser.getUserId()) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_02);
        }

        String rawPassword = requestUser.getPassword();
        if(CommonUtils.isNotEmpty(rawPassword) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_06);
        }
        if(CommonUtils.isPassword(rawPassword) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_03);
        }
        String encPassword = encoder.encode(rawPassword);
        requestUser.setPassword(encPassword);

        if(CommonUtils.isEmail(requestUser.getEmail()) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_04);
        }

        if(CommonUtils.isPhoneNum(requestUser.getPhoneNum()) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_05);
        }

        int result = userMapper.join(requestUser);

        return result;
    };



//    // 회원 찾기
//    @Override
//    @Transactional
//    public User findUser(String userId) {
//        User user = userMapper.findByUsername(userId).orElseGet(() -> {
//            return new User();
//        });
//
//        return user;
//
//    };


    // 유저 아이디 중복 체크 (blur() 처리용)
    //TODO return값 통일해야할지 프론트와 상의
    @Override
    @Transactional
    public Map<String, String> checkUserId(String userId) {
        int count = userMapper.checkUserId(userId);

        Map<String, String> resultMap = new HashMap<>();

        resultMap.put("messageType", count == 0 ? "success" : "failure");
        resultMap.put("message", count == 0 ? "사용하실 수 있는 아이디입니다." : userId+"은 이미 있는 아이디입니다.");
        return resultMap;
    };

    
    // 유저 정보 수정
    @Override
    @Transactional
    public int updateProfile(User requestUser) throws ApiException {

    	User user = userMapper.findById(requestUser.getId());

        String rawPassword = requestUser.getPassword();
        if(CommonUtils.isNotEmpty(rawPassword) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_06);
        }
        if(CommonUtils.isPassword(rawPassword) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_03);
        }

        if(CommonUtils.isEmail(requestUser.getEmail()) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_04);
        }

        if(CommonUtils.isPhoneNum(requestUser.getPhoneNum()) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_05);
        }

        if(CommonUtils.isNotEmpty(user.getName()) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_06);
        }

        if(CommonUtils.isNotEmpty(user.getPhoneNum()) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_06);
        }

        if(CommonUtils.isPhoneNum(user.getPhoneNum()) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_05);
        }

        if(CommonUtils.isNotEmpty(user.getAddress()) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_06);
        }

        if(CommonUtils.isNotEmpty(user.getEmail()) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_06);
        }
        if(CommonUtils.isEmail(user.getEmail()) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_04);
        }

        String encPassword = encoder.encode(rawPassword);
        //requestUser.setPassword(encPassword);
        user.setPassword(encPassword);
        user.setName(requestUser.getName());
        user.setPhoneNum(requestUser.getPhoneNum());
        user.setAddress(requestUser.getAddress());
        user.setEmail(requestUser.getEmail());
    	
    	int result = userMapper.updateUserInfo(user);
    	
    	// 세션 수정
    	Authentication authentication = authenticationManager.authenticate(
    			new UsernamePasswordAuthenticationToken(requestUser.getUserId(), user.getPassword())); // 강제로 로그인 처리
    	SecurityContextHolder.getContext().setAuthentication(authentication);

    	return result;
    	
    }


}
