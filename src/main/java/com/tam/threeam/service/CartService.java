package com.tam.threeam.service;

import java.util.List;
import java.util.Map;

import com.tam.threeam.model.Product;
import com.tam.threeam.response.BaseResponseDTO;
import com.tam.threeam.response.Exception.ApiException;
import org.springframework.transaction.annotation.Transactional;

import com.tam.threeam.model.Cart;

/**
 * @author 전예지
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2022/01/06
 * @
 * @ 수정일            수정자           수정내용
 * @ ———   			 ————  		  —————————————
 * @ 2022/01/06		 전예지        	최초 작성
 * @ 2022/01/07		 전예지        	장바구니 담기, 개별상품 삭제, 전체 삭제 초안
 * @ 2022/01/27		 전예지			장바구니 상품 수량 추가/차감
 * @ 2022/01/31		 전예지			장바구니 상품 수량 확인, 로그인 후 장바구니 이동
 * @ 2022/02/05	     이동은			전체상품 조회 추가
 */
public interface CartService{

	// 전체상품 조회
	@Transactional
	public BaseResponseDTO getProductList() throws ApiException;

	// 장바구니 담기
	@Transactional
    public BaseResponseDTO insertCart(Cart cartList);
	
//	//로그인 후 장바구니 이동
//	@Transactional
//	public void shiftCart(int userSeq, String cartCookieId);
	
	// 장바구니 리스트
	@Transactional
	public BaseResponseDTO getCartList();
	
//	// 장바구니 개별 상품별 총 가격
//	@Transactional
//	public Integer getTotalPrice(int userSeq);
	
	// 장바구니 상품 수량 추가
	@Transactional
	public BaseResponseDTO plusProductQty(int cartSeq);
	
	// 장바구니 상품 수량 차감
	@Transactional
	public BaseResponseDTO minusProductQty(int cartSeq);
	
//	// 장바구니 상품 수량 확인
//	public int checkQty(int id);
	
	// 장바구니 개별 상품 삭제
	@Transactional
	public BaseResponseDTO deleteOne(int cartSeq);
	
	// 회원 장바구니 전체 삭제
	@Transactional
	public BaseResponseDTO deleteAllByUserSeq();
	
//	// 비회원 장바구니 전체 삭제
//	@Transactional
//	public Map<String, String> deleteAllByCookieId(String cartCookieId);
}
