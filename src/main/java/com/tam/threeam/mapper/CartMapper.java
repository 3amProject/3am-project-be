package com.tam.threeam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
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
 * @ 2022/01/06		   전예지        최초 작성
 * @ 2022/01/07		   전예지        장바구니 담기, 개별상품 삭제, 전체 삭제
 */
@Mapper
public interface CartMapper {

	// 장바구니 담기
	void insertCart(Cart cart);
	
	// 장바구니 리스트
	List<Cart> getCartList(int requestUserSeq);
	
	// TODO 가격
	int getTotalPrice();
	
	// 장바구니 개별 상품 삭제
	void deleteOne(int id);
	
	// 장바구니 전체 삭제
	void deleteAll(int userSeq);
	
}
