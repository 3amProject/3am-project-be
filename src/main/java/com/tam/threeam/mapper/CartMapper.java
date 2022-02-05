package com.tam.threeam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
 * @ 2022/01/06		    전예지         최초 작성
 * @ 2022/01/07		    전예지         장바구니 담기, 개별상품 삭제, 전체 삭제
 * @ 2022/01/12			전예지			장바구니 개별 상품 삭제/전체 삭제 리턴 타입 변경
 * @ 2022/01/26			전예지			장바구니 주문 기한 만료 상품 삭제
 * @ 2022/01/27			전예지			장바구니 상품 수량 추가/차감, 장바구니 상품 수량 확인
 * 													장바구니 담기 로그인 여부 로직 추가, 로그인 후 장바구니 이동
 * @ 2022/02/01			전예지			비회원 장바구니 전체 삭제
 */
@Mapper
public interface CartMapper {

	// TODO 장바구니 담기 : 로그인 여부 로직 추가
	void insertCart(Cart cart);
	
	// TODO 로그인 후 장바구니 이동
	int shiftCart(@Param("userSeq") int userSeq, @Param("cartCookieId") String cartCookieId);
	
	// 장바구니 리스트
	List<Cart> getCartList(int requestUserSeq);
	
	// TODO 장바구니 개별 상품별 총 가격 - parameter : userSeq
	int getTotalPrice();
	
	// 장바구니 상품 수량 확인
	int checkQty(int id);
	
	// 장바구니 상품 수량 추가
	int plusQty(int id);
	
	// 장바구니 상품 수량 차감
	int minusQty(int id);
	
	// 장바구니 개별 상품 삭제
	int deleteOne(Cart cart);
	
	// 회원 장바구니 전체 삭제
	int deleteAllByUserSeq(int userSeq);
	
	// 비회원 장바구니 전체 삭제
	int deleteAllByCookieId(String cartCookieId);
	
	// 장바구니 주문 기한 만료 상품 삭제
	int deleteOrderExpired(int userSeq);
	
	// 주문 후 장바구니 제거
	int deleteOrder(Cart cart);
	
}
