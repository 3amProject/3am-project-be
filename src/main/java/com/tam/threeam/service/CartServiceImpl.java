package com.tam.threeam.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tam.threeam.model.Product;
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
 * @ 수정일               수정자            수정내용
 * @ ———  			    ————   		—————————————
 * @ 2022/01/06		   	전예지        	최초 작성
 * @ 2022/01/07		   	전예지        	장바구니 담기, 개별상품 삭제, 전체 삭제
 * @ 2022/01/12			전예지			장바구니 개별 삭제/전체 삭제 조건 삽입
 * @ 2022/01/27			전예지			장바구니 상품 수량 추가/차감
 * @ 2022/01/31			전예지			장바구니 상품 수량 확인, 로그인 후 장바구니 이동
 * @ 2022/02/05		 	이동은			전체상품 조회 추가
 */
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private UserMapper userMapper;

	// 전체상품 조회
	@Transactional
	@Override
	public List<Product> getProductList(){

		return cartMapper.getProductList();
	};

	// 장바구니 담기
	@Transactional
	@Override
	public Map<String, String> insertCart(Cart cart){		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("messageType", "success");
        resultMap.put("message", "장바구니에 담았습니다.");
        
        cartMapper.insertCart(cart);
		return resultMap;
	}
	
	
	// TODO 로그인 후 장바구니 이동
	@Transactional
	public void shiftCart(int userSeq, String cartCookieId) {
		cartMapper.shiftCart(userSeq, cartCookieId);
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
		
		// 장바구니 주문 기한 만료 상품 삭제
		cartMapper.deleteOrderExpired(requestUserSeq);
		
		return cartMapper.getCartList(requestUserSeq);
	}
	
	
	// TODO 장바구니 개별 상품별 총 가격 : userSeq 가져오기
	@Transactional
	@Override
	public int getTotalPrice() {
		return cartMapper.getTotalPrice();
	}
	
	
	// TODO 장바구니 상품 수량 추가
	@Transactional
	@Override
	public Map<String, String> plusQty(int id) {
		cartMapper.plusQty(id);
		Map<String, String> resultMap = new HashMap<>();
        resultMap.put("messageType", "Success");
        resultMap.put("message", "장바구니 상품 수량 추가 완료");
        return resultMap;
	}
	
	
	// TODO 장바구니 상품 수량 차감
	@Transactional
	@Override
	public Map<String, String> minusQty(int id) {
		Map<String, String> resultMap = new HashMap<>();
		if(cartMapper.checkQty(id) < 1) {
			resultMap.put("messageType", "Failure");
			resultMap.put("message", "장바구니 상품 최소 수량 1");
			return resultMap;
		}
		
		cartMapper.minusQty(id);		
		
        resultMap.put("messageType", "Success");
        resultMap.put("message", "장바구니 상품 수량 빼기 완료");
        return resultMap;
	}
	
	
	// 장바구니 상품 수량 확인
	@Transactional
	@Override
	public int checkQty(int id) {
		return cartMapper.checkQty(id);
	}
	
	
	// 장바구니 개별 상품 삭제
	@Transactional
	@Override
	public Map<String, String> deleteOne(int id){
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		UserDetails userDetails = (UserDetails)principal;
//		
//		int requestUserSeq = userMapper.findPkByUserId(userDetails.getUsername());
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("messageType", "success");
        resultMap.put("message", "해당 상품이 장바구니에서 삭제되었습니다.");
        
        Cart cart = new Cart();
        cart.setId(id);
//        cart.setUserSeq(requestUserSeq);
        
        if(cartMapper.deleteOne(cart) == 0) {
        	resultMap.put("messageType", "failure");
            resultMap.put("message", "상품 삭제에 실패했습니다.");
            return resultMap;
        }
        cartMapper.deleteOne(cart);
        return resultMap;
	}
	
	
	// 회원 장바구니 전체 삭제
	@Transactional
	@Override
	public Map<String, String> deleteAllByUserSeq(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		
		int requestUserSeq = userMapper.findPkByUserId(userDetails.getUsername());
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("messageType", "success");
        resultMap.put("message", "장바구니가 비었습니다.");
        
        if(cartMapper.deleteAllByUserSeq(requestUserSeq) == 0) {
        	resultMap.put("messageType", "failure");
            resultMap.put("message", "장바구니 비우기에 실패했습니다.");
            return resultMap;
        }
        
        cartMapper.deleteAllByUserSeq(requestUserSeq);
        return resultMap;
	}
	
	
	// 비회원 장바구니 전체 삭제
	@Transactional
	@Override
	public Map<String, String> deleteAllByCookieId(String cartCookieId){			
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("messageType", "success");
	    resultMap.put("message", "장바구니가 비었습니다.");
	        
	    if(cartMapper.deleteAllByCookieId(cartCookieId) == 0) {
	        resultMap.put("messageType", "failure");
	        resultMap.put("message", "장바구니 비우기에 실패했습니다.");
	        return resultMap;
	    }
	        
	    cartMapper.deleteAllByCookieId(cartCookieId);
	    return resultMap;
	}
	
}
