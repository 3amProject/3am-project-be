package com.tam.threeam.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.tam.threeam.model.User;
import org.apache.ibatis.annotations.Param;

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
 * @ 2022/1/4			이동은		회원가입 로직 완료
 * @ 2022/1/4			전예지		유저 고유값/아이디로 유저 찾기
 */
@Mapper
public interface UserMapper {

	// 회원 가입
	int join(User user);

	// 유저 아이디 중복 체크
	int checkUserId(String userId);

	// 유저 고유값으로 유저 찾기
	Optional<User> findUserById(int id);
	
	// 유저 아이디로 유저 찾기
	Optional<User> findUserByUserId(String userId);
	
	// 유저 아이디로 유저 고유값 찾기
	int findPkByUserId(String userId);

	// refresh token DB 저장
	int updateRefreshToken(@Param("refreshToken") String refreshToken, @Param("userId") String userId);	
	
	// 유저 정보 수정
	int updateUserInfo(User user);
}
