package com.tam.threeam.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tam.threeam.model.User;

import lombok.Data;

/**
 * @author 전예지
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2021/12/30
 * @
 * @ 수정일       	수정자        수정내용
 * @ ———   			————    	—————————————
 * @ 2021/12/30     전예지        최초 작성
 */
@Data
public class PrincipalDetail implements UserDetails {

	private User user;

	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	/*
	 * @return 계정이 갖고 있는 권한 목록 리턴
	 * */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authList = new ArrayList<>();
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authList;
	}

	@Override
	public String getUsername() {
		return user.getUserId();
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	public int getUserSeq() {
		return user.getId();
	}
	
	public String getName() {
		return user.getName();
	}

	public String getPhoneNum() {
		return user.getPhoneNum();
	}
	
	public String getAddress() {
		return user.getAddress();
	}
	
	public String getEmail() {
		return user.getEmail();
	}

	public String getRefreshToken() { return user.getRefreshToken(); }

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}	
	
}
