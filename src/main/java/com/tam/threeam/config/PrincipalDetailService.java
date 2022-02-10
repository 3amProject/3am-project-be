package com.tam.threeam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tam.threeam.mapper.UserMapper;
import com.tam.threeam.model.User;

/**
 * @author 전예지
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2021/12/30
 * @
 * @ 수정일     		 수정자      수정내용
 * @ ———   			 ————    —————————————
 * @ 2021/12/30      전예지    최초 작성
 * @ 2021/01/26		 이동은	 UsernameNotFoundException 추가
 */
@Service
public class PrincipalDetailService implements UserDetailsService{

	@Autowired
	private UserMapper userMapper;
	
	/*
	 * @return security 세션에 유저 정보 UserDetails 타입으로 저장
	 * */
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User principal = userMapper.findUserByUserId(userId).get();

		if (principal.getUserId().equals(userId)) {
			return new PrincipalDetail(principal);
		} else {
			throw new UsernameNotFoundException("User not found with username: " + userId);
		}

	}

	
}
