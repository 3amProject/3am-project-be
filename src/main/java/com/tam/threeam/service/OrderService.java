package com.tam.threeam.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.tam.threeam.model.Cart;
import com.tam.threeam.model.Order;
import com.tam.threeam.model.OrderDetail;
import com.tam.threeam.model.User;

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
 * @ 2022/01/21		전예지			주문 처리 로직 구현
 */
public interface OrderService {

	/* TODO
	 * 주문자 정보 (이름, 주소, 전화번호)
	 * 배송 상품 정보 (상품명, 가격, 수량, 총 수량, 총 가격, 배송일자)
	 * 결제 정보
	 * */
	
	// 주문 상품 정보
	@Transactional
	public List<OrderDetail> getProductInfo(List<OrderDetail> requestOrders);
	
	
	// 주문 처리
	@Transactional
	public void order(Order requestOrder);
	
	
	
	
	
	
	/*
	 * // 기본 주문 정보 기입
	 * 
	 * @Transactional public Map<String, String> insertOrder();
	 * 
	 * // 주문자 정보
	 * 
	 * @Transactional public Map<String, String> getUserInfo(int userSeq);
	 * 
	 * // 주문 상품 정보
	 * 
	 * @Transactional public List<Cart> getOrderList(int cartSeq);
	 */
	
}