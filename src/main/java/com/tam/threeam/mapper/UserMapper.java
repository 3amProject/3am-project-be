package com.tam.threeam.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.tam.threeam.model.User;

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
 * @ 2022/1/4			전예지		유저 고유값/아이디로 유저 찾기
 */
@Mapper
public interface UserMapper {
	
	// 유저 고유값으로 유저 찾기
	User findById(int id);
	
	// 유저 아이디로 유저 찾기
	Optional<User> findByUserId(String userId);
	
	// 유저 정보 조회
	
	
	
	// 유저 정보 수정
	int updateUserInfo(User user);
}
