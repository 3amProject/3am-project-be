package com.tam.threeam.service;

import com.tam.threeam.model.User;

import com.tam.threeam.response.BaseResponseDTO;
import com.tam.threeam.response.Exception.ApiException;
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
 * @ 2021/12/30			이동은        	최초 작성
 * @ 2022/01/03			전예지			유저 정보 수정
 * @ 2022/01/25			전예지			마이페이지 조회 구현
 * @ 2022/01/31			전예지			userId로 유저 고유값 찾기
 * @ 2022/02/09			전예지			유저 정보 수정 페이지
 */
public interface UserService {

    // 사용자 추가 / 회원가입
    @Transactional
    public BaseResponseDTO join(User user);
    
    // userId로 유저 고유값 찾기
    @Transactional
    public int findUserPk(String userId);
    
    // 주문자(유저) 정보 찾기
    @Transactional
    public User findUser(int userSeq);

    // 로그인, 토큰발급
    @Transactional
    public BaseResponseDTO signIn(User user) throws ApiException;

    // refresh토큰 재발급
    @Transactional
    public BaseResponseDTO refreshToken(User user) ;

    // refresh토큰 DB에 추가
    @Transactional
    public int updateRefreshToken(String refreshToken, String userId);

    // 유저 아이디 중복 체크
    @Transactional
    public Map<String, String> checkUserId(String userId);
    
    // 마이페이지 조회
    @Transactional
    public BaseResponseDTO myPage();
    
    // 유저 정보 수정페이지
    @Transactional
    public BaseResponseDTO updateProfileForm();
    
    // 유저 정보 수정
    @Transactional
    public BaseResponseDTO updateProfile(User requestUser);
}
