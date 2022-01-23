package com.tam.threeam.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tam.threeam.mapper.CartMapper;
import com.tam.threeam.mapper.UserMapper;
import com.tam.threeam.model.Cart;


/**
 * @author 전예지
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2022/01/06
 * @
 * @ 수정일         수정자                   수정내용
 * @ ———    ————    —————————————
 * @ 2022/01/06		   	전예지        	최초 작성
 * @ 2022/01/07		   	전예지        	장바구니 담기, 개별상품 삭제, 전체 삭제
 * @ 2022/01/12			전예지			장바구니 개별 삭제/전체 삭제 조건 삽입
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
		UserDetails userDetails = (UserDetails)principal;
		
		int requestUserSeq = userMapper.findPkByUserId(userDetails.getUsername());
		cart.setUserSeq(requestUserSeq);
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("messageType", "success");
        resultMap.put("message", "장바구니에 담았습니다.");
        
        // TODO 장바구니 조건문
        if(userDetails.getUsername() == null) {
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
		//세션에서 getUsername -> username으로 getUserSeq -> userSeq로 getCartList
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		
		int requestUserSeq = userMapper.findPkByUserId(userDetails.getUsername());
		
		return cartMapper.getCartList(requestUserSeq);
	}
	
	
	// TODO 장바구니 전체 가격 : userSeq 가져오기
	@Transactional
	public int getTotalPrice() {
		return cartMapper.getTotalPrice();
	}
	
	
	// 장바구니 개별 상품 삭제
	@Transactional
	@Override
	public Map<String, String> deleteOne(int id){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		
		int requestUserSeq = userMapper.findPkByUserId(userDetails.getUsername());
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("messageType", "success");
        resultMap.put("message", "해당 상품이 장바구니에서 삭제되었습니다.");
        
        Cart cart = new Cart();
        cart.setId(id);
        cart.setUserSeq(requestUserSeq);
        
        if(cartMapper.deleteOne(cart) == 0) {
        	resultMap.put("messageType", "failure");
            resultMap.put("message", "상품 삭제에 실패했습니다.");
            return resultMap;
        }
        cartMapper.deleteOne(cart);
        return resultMap;
	}
	
	
	// 장바구니 전체 삭제
	@Transactional
	@Override
	public Map<String, String> deleteAll(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		
		int requestUserSeq = userMapper.findPkByUserId(userDetails.getUsername());
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("messageType", "success");
        resultMap.put("message", "장바구니가 비었습니다.");
        
        if(cartMapper.deleteAll(requestUserSeq) == 0) {
        	resultMap.put("messageType", "failure");
            resultMap.put("message", "장바구니 비우기에 실패했습니다.");
            return resultMap;
        }
        
        cartMapper.deleteAll(requestUserSeq);
        return resultMap;
	}
	
}
