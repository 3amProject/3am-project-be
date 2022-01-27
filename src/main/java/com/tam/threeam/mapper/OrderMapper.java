package com.tam.threeam.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.tam.threeam.model.Order;
import com.tam.threeam.model.OrderDetail;

/**
 * @author 전예지
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2022/01/19
 * @
 * @ 수정일       수정자                   수정내용
 * @ ———    ————    —————————————
 * @ 2022/01/19		전예지       	최초 작성
 * @ 2022/01/21		전예지			주문 처리, 주문 테이블, 주문 상세 테이블
 */
@Mapper
public interface OrderMapper {

	/* TODO
	 * 주문자 정보 (이름, 주소, 전화번호)
	 * 배송 상품 정보 (상품명, 가격, 수량, 총 수량, 총 가격, 배송일자)
	 * 결제 정보
	 * */
	
	// 주문 상품 정보(주문 페이지)
	OrderDetail getProductInfo(int productSeq);
	
	/* 주문 정보(주문 처리)
	 * @ 상품 고유값
	 * @ 상품 가격
	 * */
	OrderDetail getOrderInfo(int productSeq);
	
	// 주문 테이블 등록
	int insertOrder(Order order);
	
	// 주문 상세 테이블 등록
	int insertOrderDetail(OrderDetail orderDetail);
	
}