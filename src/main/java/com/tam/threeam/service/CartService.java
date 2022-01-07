package com.tam.threeam.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.tam.threeam.model.Cart;
import com.tam.threeam.model.User;

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
public interface CartService{

	// 장바구니 담기
	@Transactional
    public Map<String, String> insertCart(Cart cart);
	
	// 장바구니 리스트
	@Transactional
	public List<Cart> getCartList();
	
	// 장바구니 개별 상품 삭제
	@Transactional
	public Map<String, String> deleteOne(int id);
	
	// 장바구니 전체 삭제
	@Transactional
	public Map<String, String> deleteAll(int userSeq);
}
