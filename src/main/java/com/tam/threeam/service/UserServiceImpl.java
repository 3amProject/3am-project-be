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
    	User newUser = userMapper.findById(requestUser.getId()); // 유저 고유값으로 유저 찾기
    	
    	String rawPassword = requestUser.getPassword();
        String encPassword = encoder.encode(rawPassword);
    	
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("messageType", "success");
        resultMap.put("message", "회원 정보 수정이 완료되었습니다.");
        
        // TODO 비밀번화 해쉬화 전 유효성 검사 작성
//        if(CommonUtils.isPassword(rawPassword) == false) {
//    		resultMap.put("messageType", "failure");
//			resultMap.put("message", "비밀번호는 3자 이상 12자 이하의 숫자, 영어 대/소문자로 입력해주세요.");
//    	}
        
        newUser.setPassword(encPassword);
    	newUser.setName(requestUser.getName());
    	newUser.setPhoneNum(requestUser.getPhoneNum());
    	newUser.setAddress(requestUser.getAddress());
    	newUser.setEmail(requestUser.getEmail());
    	
    	if(CommonUtils.isNotEmpty(newUser.getName()) == false) {
			resultMap.put("messageType", "failure");
			resultMap.put("message", "이름을 입력해주세요.");
			
			return resultMap;
		}
    	
    	// TODO 전화번호 유효성 검사 utils 작성 후 반영
    	if(CommonUtils.isNotEmpty(newUser.getPhoneNum()) == false) {
			resultMap.put("messageType", "failure");
			resultMap.put("message", "전화번호를 입력해주세요.");
			return resultMap;
		}
		if(CommonUtils.isEmail(newUser.getPhoneNum()) == false) {
			resultMap.put("messageType", "failure");
			resultMap.put("message", "전화번호를 형식에 맞게 입력해주세요.");
			return resultMap;
		}
    	
		if(CommonUtils.isNotEmpty(newUser.getAddress()) == false) {
			resultMap.put("messageType", "failure");
			resultMap.put("message", "주소를 입력해주세요.");
			return resultMap;
		}
		
    	if(CommonUtils.isNotEmpty(newUser.getEmail()) == false) {
			resultMap.put("messageType", "failure");
			resultMap.put("message", "이메일을 입력해주세요.");
			return resultMap;
		}
		if(CommonUtils.isEmail(newUser.getEmail()) == false) {
			resultMap.put("messageType", "failure");
			resultMap.put("message", "이메일을 형식에 맞게 입력해주세요.");
			return resultMap;
		}
    	
    	userMapper.updateUserInfo(newUser);
    	
    	
    	// 세션 등록 : DB 값이 변경된 다음에 해야 함
    	// 토큰 통해서 아이디, 패스워드 날려서 authentication 객체 만들어지면서 세션에 등록
    	Authentication authentication = authenticationManager.authenticate(
    			new UsernamePasswordAuthenticationToken(requestUser.getUserId(), requestUser.getPassword())); // 강제로 로그인 처리
    	SecurityContextHolder.getContext().setAuthentication(authentication);

    	return resultMap;
    	
    }


}
