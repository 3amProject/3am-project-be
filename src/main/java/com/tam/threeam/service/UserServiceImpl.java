package com.tam.threeam.service;

<<<<<<< HEAD
=======
import com.tam.threeam.config.auth.PrincipalDetail;
import com.tam.threeam.mapper.OrderMapper;
>>>>>>> fetch_head
import com.tam.threeam.mapper.UserMapper;
import com.tam.threeam.model.User;
import com.tam.threeam.response.Exception.ApiException;
import com.tam.threeam.response.ExceptionEnum;
import com.tam.threeam.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 이동은
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2021/12/30
 * @
 * @ 수정일            수정자          수정내용
 * @ ———             ————       —————————————
 * @ 2021/12/30	    이동은       	최초 작성
 * @ 2022/01/03	 	전예지			유저 정보 수정
 * @ 2022/01/04		이동은			회원가입 로직 완료
 * @ 2022/01/04		전예지			유저 정보 수정 세션 반영
 * @ 2022/01/07       	이동은     	전화번호 양식 validation check 로직 추가
 * @ 2022/01/19       	이동은       	validation ExceptionHandler로 처리
 * @ 2022/01/25		전예지			마이페이지 조회 구현
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    
    // 회원가입
    @Override
    @Transactional
    public Map<String, String> join(User requestUser) throws ApiException {

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

        userMapper.join(requestUser);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("messageType", "Success");
        resultMap.put("message", "회원가입 완료");


        return resultMap;
    };


//    // userId로 유저 찾기
//    @Override
//    @Transactional
//    public User findUser(String userId) {
//        User user = userMapper.findByUserId(userId).orElseGet(() -> {
//            return new User();
//        });
//
//        return user;
//
//    };

    // user sequence로 주문자(유저) 정보 찾기
    @Override
    @Transactional
    public User findUser(int userSeq) {

        return userMapper.findUserById(userSeq).orElseGet(() -> {
            return new User();
        });
    };


    // 유저 아이디 중복 체크 (blur() 처리용)
    //TODO return값 통일해야할지 프론트와 상의
    @Override
    @Transactional
    public Map<String, String> checkUserId(String userId) {
        int count = userMapper.checkUserId(userId);

        Map<String, String> resultMap = new HashMap<>();

        resultMap.put("messageType", count == 0 ? "Success" : "Failure");
        resultMap.put("message", count == 0 ? "사용하실 수 있는 아이디입니다." : userId+"은 이미 있는 아이디입니다.");
        return resultMap;
    };
    
    
    // TODO 마이페이지 조회
    @Override
    @Transactional
    public Map<String, Object> myPage(){
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;		
		int requestUserSeq = userMapper.findPkByUserId(userDetails.getUsername());
		
		Map<String, Object> resultMap = new HashMap<>();
		
		// 유저 정보
		resultMap.put("userInfo", userMapper.findUserById(requestUserSeq));
		
		// 주문 내역 조회
		resultMap.put("orderHistory", orderMapper.getOrderHistory(requestUserSeq));

    	return resultMap;
    } 
    
    
    // 유저 정보 수정
    @Override
    @Transactional
    public Map<String, String> updateProfile(User requestUser) throws ApiException {

    	User user = userMapper.findUserById(requestUser.getId()).orElseGet(() -> {
            return new User();
        });
        if (user.getUserId() == null) {
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("messageType", "Failure");
            resultMap.put("message", "사용자 정보를 찾을 수 없습니다.");

            return resultMap;
        }

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
    	
    	userMapper.updateUserInfo(user);
    	
    	// 세션 수정
    	Authentication authentication = authenticationManager.authenticate(
    			new UsernamePasswordAuthenticationToken(requestUser.getUserId(), user.getPassword())); // 강제로 로그인 처리
    	SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("messageType", "Success");
        resultMap.put("message", "회원정보 수정 완료");

    	return resultMap;
    	
    }


}
