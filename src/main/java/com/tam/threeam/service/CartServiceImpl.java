package com.tam.threeam.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tam.threeam.config.auth.PrincipalDetail;
import com.tam.threeam.mapper.CartMapper;
import com.tam.threeam.mapper.UserMapper;
import com.tam.threeam.model.Cart;
import com.tam.threeam.model.User;
import com.tam.threeam.util.CommonUtils;

/**
 * @author 전예지
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2022/01/06
 * @
 * @ 수정일         수정자                   수정내용
 * @ ———    ————    —————————————
 * @ 2022/01/06		   전예지        최초 작성
 * @ 2022/01/07		   전예지        장바구니 담기, 개별상품 삭제, 전체 삭제
 */
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	// 장바구니 담기
	@Transactional
	@Override
	public Map<String, String> insertCart(Cart cart){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PrincipalDetail principalDetail = (PrincipalDetail)principal;
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("messageType", "success");
        resultMap.put("message", "장바구니에 담았습니다.");
        
        // TODO 장바구니 조건문
        if(principalDetail.getUsername() == null) {
        	resultMap.put("messageType", "failure");
			resultMap.put("message", "로그인 후 이용 가능합니다.");
			return resultMap;
        }
        
        cartMapper.insertCart(cart);
		return resultMap;
	}
	
	
	// 장바구니 리스트
	@Transactional
	@Override
	public List<Cart> getCartList(){
		// TODO userSeq 가져오기 : username(유저 아이디)으로 DB에서 조회해서 찾기
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		
		//세션에서 getUsername -> username으로 getUserSeq -> userSeq로 getCartList
		
		int requestUserSeq = userMapper.findPkByUserId(userDetails.getUsername());
//		Cart cart = new Cart();
//		cart.setUserSeq(principalDetail.getUserSeq());
		
		return cartMapper.getCartList(requestUserSeq);
	}
	
	
	// 장바구니 개별 상품 삭제
	@Transactional
	@Override
	public Map<String, String> deleteOne(int id){
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("messageType", "success");
        resultMap.put("message", "해당 상품이 장바구니에서 삭제되었습니다.");
        cartMapper.deleteOne(id);
        return resultMap;
	}
	
	
	// 장바구니 전체 삭제
	@Transactional
	@Override
	public Map<String, String> deleteAll(int userSeq){
//		// TODO userSeq 가져오기
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		PrincipalDetail principalDetail = (PrincipalDetail)principal;
//		
//		Cart cart = new Cart();
//		cart.setUserSeq(principalDetail.getUserSeq());
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("messageType", "success");
        resultMap.put("message", "장바구니가 비었습니다.");
        cartMapper.deleteAll(userSeq);
        return resultMap;
	}
	
}