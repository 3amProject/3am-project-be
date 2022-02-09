package com.tam.threeam.service;


import com.tam.threeam.config.JwtTokenUtil;
import com.tam.threeam.config.auth.PrincipalDetail;
import com.tam.threeam.config.auth.PrincipalDetailService;
import com.tam.threeam.mapper.OrderMapper;
import com.tam.threeam.mapper.UserMapper;
import com.tam.threeam.model.User;
import com.tam.threeam.response.BaseResponseDTO;
import com.tam.threeam.response.Exception.ApiException;
import com.tam.threeam.response.ExceptionEnum;
import com.tam.threeam.response.UserResponseDto;
import com.tam.threeam.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
 * @ 2021/12/30	    이동은         	최초 작성
 * @ 2022/01/03	 	전예지			유저 정보 수정
 * @ 2022/01/04		이동은			회원가입 로직 완료
 * @ 2022/01/04		전예지			유저 정보 수정 세션 반영
 * @ 2022/01/07     이동은         	전화번호 양식 validation check 로직 추가
 * @ 2022/01/19     이동은           	validation ExceptionHandler로 처리
 * @ 2022/01/25		전예지			마이페이지 조회 구현
 * @ 2022/01/31		전예지		userId로 유저 고유값 찾기
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private BCryptPasswordEncoder encoder;

    
    // 회원가입
    @Override
    @Transactional
    public Map<String, String> join(User requestUser) throws ApiException {

//        if(CommonUtils.isNotEmpty(requestUser.getUserId()) == false) {
//            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_06);
//        }
//        if (userMapper.checkUserId(requestUser.getUserId()) != 0) {
//            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_01);
//        }
//        if(CommonUtils.isUserId(requestUser.getUserId()) == false) {
//            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_02);
//        }

        String rawPassword = requestUser.getPassword();
//        if(CommonUtils.isNotEmpty(rawPassword) == false) {
//            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_06);
//        }
//        if(CommonUtils.isPassword(rawPassword) == false) {
//            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_03);
//        }
        String encPassword = encoder.encode(rawPassword);
        requestUser.setPassword(encPassword); // Jwt 테스트 위해 비밀번호 해시화 없이 진행 중

//        if(CommonUtils.isEmail(requestUser.getEmail()) == false) {
//            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_04);
//        }
//
//        if(CommonUtils.isPhoneNum(requestUser.getPhoneNum()) == false) {
//            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_05);
//        }

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

    // userId로 유저 고유값 찾기
    @Override
    @Transactional
    public int findUserPk(String userId) {
    	return userMapper.findPkByUserId(userId);
    }
    
    
    
    // user sequence로 주문자(유저) 정보 찾기
    @Override
    @Transactional
    public User findUser(int userSeq) {

        return userMapper.findUserById(userSeq).orElseGet(() -> {
            return new User();
        });
    };

    @Override
    @Transactional
    // 로그인, 토큰발급
    public BaseResponseDTO signIn(User user) throws ApiException {
        log.info("userId: {}, password: {}", user.getUserId(), user.getPassword());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword()));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return BaseResponseDTO.fail("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        final UserDetails userDetails = principalDetailService.loadUserByUsername(user.getUserId());

        UserResponseDto.TokenInfo tokenInfo = jwtTokenUtil.generateToken(userDetails);
        tokenInfo.userId = user.getUserId();

        updateRefreshToken(tokenInfo.getRefreshToken(), user.getUserId());

        return BaseResponseDTO.success(tokenInfo);
    }

    // refresh토큰 재발급
    @Override
    @Transactional
    public BaseResponseDTO refreshToken(User user) throws ApiException {
        final Authentication authentication = jwtTokenUtil.getAuthentication();
        String currentUserId = authentication.getName();

        log.info("getPrincipal: {}", authentication.getPrincipal());
        log.info("getAuthorities: {}", authentication.getAuthorities());
        log.info("getDetails: {}", authentication.getDetails());
        log.info("getCredentials: {}", authentication.getCredentials());
        log.info("getCurrentRefreshToken: {}", user.getRefreshToken() );

        if (!StringUtils.hasText(user.getUserId()) || !StringUtils.hasText(user.getRefreshToken())) {
            return BaseResponseDTO.builder()
                    .code("BD001")
                    .messageType(BaseResponseDTO.FAIL)
                    .message("잘못된 요청입니다.")
                    .build();
        }

        // accessToken과 userId가 다른 경우
        if (!currentUserId.equals(user.getUserId())) {
            return BaseResponseDTO.builder()
                    .code("BD002")
                    .messageType(BaseResponseDTO.FAIL)
                    .message("잘못된 요청입니다.")
                    .build();
        }

        if (!jwtTokenUtil.validateToken(user.getRefreshToken())) {
            return BaseResponseDTO.builder()
                    .code("ER002")
                    .messageType(BaseResponseDTO.FAIL)
                    .message("Refresh Token 정보가 일치하지 않습니다.")
                    .build();
        }

        User currentUser = userMapper.findUserByUserId(user.getUserId()).get();
        if (!currentUser.getRefreshToken().equals(user.getRefreshToken())) {
            return BaseResponseDTO.builder()
                    .code("ER003")
                    .messageType(BaseResponseDTO.FAIL)
                    .message("Refresh Token 정보가 일치하지 않습니다.")
                    .build();
        }

        UserDetails userDetails = new PrincipalDetail(currentUser);
        UserResponseDto.TokenInfo tokenInfo = jwtTokenUtil.generateToken(userDetails);
        tokenInfo.userId = user.getUserId();

        log.info("getNewtokenInfo: {}", tokenInfo);

        updateRefreshToken(tokenInfo.refreshToken, user.getUserId());
        return BaseResponseDTO.success(tokenInfo);
    }


    // refresh토큰 DB에 추가
    @Override
    @Transactional
    public int updateRefreshToken(String refreshToken, String userId) {
        return userMapper.updateRefreshToken(refreshToken, userId);
    }


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
    	final Authentication authentication = jwtTokenUtil.getAuthentication();
        String currentUserId = authentication.getName();
        int currentUserSeq = userMapper.findPkByUserId(currentUserId);
		
		Map<String, Object> resultMap = new HashMap<>();
		
		// 유저 정보
		resultMap.put("userInfo", userMapper.findUserById(currentUserSeq));
		
		// 주문 내역 조회
		resultMap.put("orderHistory", orderMapper.getOrderHistory(currentUserSeq));

    	return resultMap;
    } 
    
    
    // 유저 정보 수정
    @Override
    @Transactional
    public Map<String, String> updateProfile(User requestUser) throws ApiException {
    	final Authentication authentication = jwtTokenUtil.getAuthentication();
    	User user = userMapper.findUserById(requestUser.getId()).orElseGet(() -> {
            return new User();
        });
    	
        if (authentication.getName() == null) {
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

//        
        if(CommonUtils.isNotEmpty(user.getName()) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_06);
        }

        if(CommonUtils.isNotEmpty(requestUser.getPhoneNum()) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_06);
        }

        if(CommonUtils.isPhoneNum(requestUser.getPhoneNum()) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_05);
        }

        if(CommonUtils.isNotEmpty(requestUser.getAddress()) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_06);
        }

        if(CommonUtils.isNotEmpty(requestUser.getEmail()) == false) {
            throw new ApiException(ExceptionEnum.INVALID_SIGNUP_INPUT_06);
        }
        if(CommonUtils.isEmail(requestUser.getEmail()) == false) {
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
    	
//    	// 세션 수정
//    	Authentication authentication = authenticationManager.authenticate(
//    			new UsernamePasswordAuthenticationToken(requestUser.getUserId(), user.getPassword())); // 강제로 로그인 처리
//    	SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("messageType", "Success");
        resultMap.put("message", "회원정보 수정 완료");

    	return resultMap;
    	
    }


}
