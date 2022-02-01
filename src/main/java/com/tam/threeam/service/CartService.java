package com.tam.threeam.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

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
 * @ 2022/01/06		  	전예지        	최초 작성
 * @ 2022/01/07		   	전예지        	장바구니 담기, 개별상품 삭제, 전체 삭제
 * @ 2022/01/27			전예지			장바구니 상품 수량 추가/차감
 * @ 2022/01/31			전예지			장바구니 상품 수량 확인, 로그인 후 장바구니 이동
 */
public interface CartService{

	// 장바구니 담기
	@Transactional
    public Map<String, String> insertCart(Cart cart);
	
	// TODO 로그인 후 장바구니 이동
	@Transactional
	public void shiftCart(int userSeq, String cartCookieId);
	
	// 장바구니 리스트
	@Transactional
	public List<Cart> getCartList();
	
	// TODO 장바구니 개별 상품별 총 가격
	@Transactional
	public int getTotalPrice();
	
	// TODO 장바구니 상품 수량 추가
	@Transactional
	public Map<String, String> plusQty(int id);
	
	// TODO 장바구니 상품 수량 차감
	public Map<String, String> minusQty(int id);
	
	// 장바구니 상품 수량 확인
	public int checkQty(int id);
	
	// 장바구니 개별 상품 삭제
	@Transactional
	public Map<String, String> deleteOne(int id);
	
	// 장바구니 전체 삭제
	@Transactional
	public Map<String, String> deleteAll();
}
