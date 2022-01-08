package com.tam.threeam.service;

import com.tam.threeam.mapper.UserMapper;
import com.tam.threeam.model.User;
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
 * @ 수정일         수정자                   수정내용
 * @ ———    ————    —————————————
 * @ 2021/12/30		    이동은        최초 작성
 * @ 2022/1/3			전예지		유저 정보 수정
 * @ 2022/1/4			이동은		회원가입 로직 완료
 * @ 2022/1/4			전예지		유저 정보 수정 세션 반영
 * @ 2022/1/7           이동은        전화번호 양식 validation check 로직 추가
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
    public Map<String, String> join(User requestUser) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("messageType", "success");
        resultMap.put("message", "회원가입이 완료되었습니다.");

        if (userMapper.checkUserId(requestUser.getUserId()) != 0) {
            resultMap.put("messageType", "failure");
            resultMap.put("message", requestUser.getUserId()+"은 이미 있는 아이디입니다.");

        }

        if(CommonUtils.isUserId(requestUser.getUserId()) == false) {
            resultMap.put("messageType" , "failure");
            resultMap.put("message", "아이디는 3자 이상 12자 이하의 숫자, 영어 대/소문자로 입력해주세요.");

            return resultMap;
        }

        String rawPassword = requestUser.getPassword();
        if(CommonUtils.isPassword(rawPassword) == false) {
            resultMap.put("messageType", "failure");
            resultMap.put("message", "비밀번호는 3자 이상 12자 이하의 숫자, 영어 대/소문자로 입력해주세요.");

            return resultMap;
        }
        String encPassword = encoder.encode(rawPassword);
        requestUser.setPassword(encPassword);

        if(CommonUtils.isNotEmpty(requestUser.getEmail()) == false) {
            resultMap.put("messageType", "failure");
            resultMap.put("message", "이메일을 형식에 맞게 입력해주세요.");

            return resultMap;
        }

        if(CommonUtils.isPhoneNum(requestUser.getPhoneNum()) == false) {
            resultMap.put("messageType", "failure");
            resultMap.put("message", "전화번호를 형식에 맞게 입력해주세요.");
            return resultMap;
        }

        userMapper.join(requestUser);
        return resultMap;

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


    // 유저 아이디 중복 체크
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
    public Map<String, String> updateProfile(User requestUser) {
    	User user = userMapper.findById(requestUser.getId()); // 유저 고유값으로 유저 찾기
    	
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("messageType", "success");
        resultMap.put("message", "회원 정보 수정이 완료되었습니다.");

        String rawPassword = requestUser.getPassword();

        if(CommonUtils.isPassword(rawPassword) == false) {
    		resultMap.put("messageType", "failure");
			resultMap.put("message", "비밀번호는 3자 이상 12자 이하의 숫자, 영어 대/소문자로 입력해주세요.");
    	}
        String encPassword = encoder.encode(rawPassword);

        user.setPassword(encPassword);
        user.setName(requestUser.getName());
        user.setPhoneNum(requestUser.getPhoneNum());
        user.setAddress(requestUser.getAddress());
        user.setEmail(requestUser.getEmail());

    	
    	if(CommonUtils.isNotEmpty(user.getName()) == false) {
			resultMap.put("messageType", "failure");
			resultMap.put("message", "이름을 입력해주세요.");
			
			return resultMap;
		}

    	if(CommonUtils.isNotEmpty(user.getPhoneNum()) == false) {
			resultMap.put("messageType", "failure");
			resultMap.put("message", "전화번호를 입력해주세요.");
			return resultMap;
		}

		if(CommonUtils.isPhoneNum(user.getPhoneNum()) == false) {
			resultMap.put("messageType", "failure");
			resultMap.put("message", "전화번호를 형식에 맞게 입력해주세요.");
			return resultMap;
		}
    	
		if(CommonUtils.isNotEmpty(user.getAddress()) == false) {
			resultMap.put("messageType", "failure");
			resultMap.put("message", "주소를 입력해주세요.");
			return resultMap;
		}
		
    	if(CommonUtils.isNotEmpty(user.getEmail()) == false) {
			resultMap.put("messageType", "failure");
			resultMap.put("message", "이메일을 입력해주세요.");
			return resultMap;
		}
		if(CommonUtils.isEmail(user.getEmail()) == false) {
			resultMap.put("messageType", "failure");
			resultMap.put("message", "이메일을 형식에 맞게 입력해주세요.");
			return resultMap;
		}
    	
    	userMapper.updateUserInfo(user);
    	
    	// 세션 수정
    	Authentication authentication = authenticationManager.authenticate(
    			new UsernamePasswordAuthenticationToken(requestUser.getUserId(), requestUser.getPassword())); // 강제로 로그인 처리
    	SecurityContextHolder.getContext().setAuthentication(authentication);

    	return resultMap;
    	
    }


}
