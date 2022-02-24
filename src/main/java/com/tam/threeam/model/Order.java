package com.tam.threeam.model;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 전예지
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2022/01/19
 * @
 * @ 수정일       	수정자           수정내용
 * @ ———    		————  		—————————————
 * @ 2022/01/19     전예지       	최초 작성
 * @ 2022/01/21		전예지		주문 번호, 주문 상품 리스트 변수 생성
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	// 주문 고유값(주문 번호)
	private String id;
	
	// 주문자 고유값
	private int userSeq;
	
	// 주문자 이름
	private String username;
	
	// 주문자 주소
	private String address;
	
	// 주문자 번호
	private String phoneNum;
	
	// 주문자 이메일
	private String email;
	
	// 주문 상품 리스트
	private List<OrderDetail> orderDetails;
	
	// 배송일
	private Date deliveryDate;
	
	// 주문일
	private Date orderDate;
	
	/* 생성 데이터
	 *	@ 총 주문 가격 
	 */
	private int orderTotalPrice;
	
	
}
