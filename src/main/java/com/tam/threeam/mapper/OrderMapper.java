package com.tam.threeam.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
 * @ 2022/01/26		전예지			주문 내역 전체 조회
 * @ 2022/01/27		전예지			주문 상품(orderDetail) 조회 작성, 주문 내역 전체 조회 수정
 */
@Mapper
public interface OrderMapper {
	
	// 주문 상품 정보(주문 페이지)
	OrderDetail getProductInfo(int productSeq);
	
	// 주문 테이블 등록(주문 처리)
	int insertOrder(Order order);
	
	// 주문 상세 테이블 등록(주문 처리)
	int insertOrderDetail(OrderDetail orderDetail);
	
	// 주문 내역 전체 조회
	List<Order> getOrderHistory(int userSeq);
	
	// 주문 상품(orderDetail) 조회
	OrderDetail getOrderDetail(String orderSeq);
	
}
