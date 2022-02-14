package com.tam.threeam.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 전예지
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2022/01/20
 * @
 * @ 수정일       수정자                   수정내용
 * @ ———    ————    —————————————
 * @ 2022/01/20     	전예지       	최초 작성
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {

	// 주문 상세 고유값
	private int id;
	
	// 주문 테이블 고유값(주문 번호)
	private String orderSeq;
	
	/* view에서 전달받을 값
	 * @ 상품 고유값
	 * @ 상품 수량
	 */
	private int productSeq;
	
	private int productQty;
	
	/* DB에서 꺼낼 값
	 * @ 상품명
	 * @ 상품 가격
	 */
	private String productName;
	
	private int productPrice;
	
	/* 만들어서 view로 전달할 값
	 * @ 상품별 총 가격
	 */
	private int totalPrice;
	
}
