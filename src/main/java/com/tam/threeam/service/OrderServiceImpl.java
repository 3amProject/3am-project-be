package com.tam.threeam.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tam.threeam.mapper.CartMapper;
import com.tam.threeam.mapper.OrderMapper;
import com.tam.threeam.mapper.UserMapper;
import com.tam.threeam.model.Cart;
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
 * @ 2022/01/19		전예지			최초 작성
 * @ 2022/01/21		전예지			주문 처리 로직 구현
 */
@Service
public class OrderServiceImpl implements OrderService {

	/* TODO
	 * 주문자 정보 (이름, 주소, 전화번호)
	 * 배송 상품 정보 (상품명, 가격, 수량, 총 수량, 총 가격, 배송일자)
	 * 결제 정보
	 * */
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CartMapper cartMapper;
	
	// 주문 상품 정보
	@Override
	@Transactional
	public List<OrderDetail> getProductInfo(List<OrderDetail> requestOrders){
		List<OrderDetail> resultOrder = new ArrayList<>();
		for(OrderDetail orderDetail : requestOrders) {
			// 주문 상품 정보 select문 호출해 반환받은 객체 productInfo 변수에 저장
			OrderDetail productInfo = orderMapper.getProductInfo(orderDetail.getProductSeq());
			
			// 주문 수량 view에서 받아 대입
			productInfo.setProductQty(orderDetail.getProductQty());
			
			// 상품별 총 가격
			productInfo.setTotalPrice(orderDetail.getProductPrice()*orderDetail.getProductQty());
			
			// 상품 정보 세팅된 OrderDetail 객체 List 객체인 resultOrder에 요소로 추가
			resultOrder.add(productInfo);
		}

		return resultOrder; 
	}
	
	
	// TODO order 메서드 리턴 타입 변경 예정
	// 주문 처리
	@Override
	@Transactional
	public void order(Order requestOrder) {
		/* TODO
		 * 사용할 데이터 세팅(user 객체, order 객체)
		 * 주문 데이터 DB에 등록
		 * 장바구니 상품 정보 DB 제거
		 * */
		
		/* 사용할 데이터 세팅
		 * @ 유저 정보		// User user = userMapper.findUserById(requestOrder.getUserSeq()); // TODO 시큐리티 유저 삽입 확인
		 * @ 주문 정보
		 * @ Order 세팅
		 * */
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		int requestUserSeq = userMapper.findPkByUserId(userDetails.getUsername());
		requestOrder.setUserSeq(requestUserSeq);
		
		int orderTotalPrice = requestOrder.getOrderTotalPrice();
		
		List<OrderDetail> orders = new ArrayList<>();
		for(OrderDetail orderDetail : requestOrder.getOrders()) {
			OrderDetail orderInfo = orderMapper.getOrderInfo(orderDetail.getProductSeq());
			// 수량 세팅
			orderInfo.setProductQty(orderDetail.getProductQty());
			// 상품별 총 가격
			orderInfo.setTotalPrice(orderDetail.getProductPrice()*orderDetail.getProductQty());
			// List 객체 추가
			orders.add(orderInfo);
			// 주문 총 가격 추가
			orderTotalPrice += orderInfo.getTotalPrice();
		}
		requestOrder.setOrders(orders);
		requestOrder.setOrderTotalPrice(orderTotalPrice);

		
		// TODO 배송일 등록 : view에서 받아서 처리해야 함
		
		
		/* 주문 데이터 DB에 등록
		 * @ order 고유값(주문번호) 생성 && 저장
		 * @ 세팅된 주문 객체 DB 데이터 등록
		 */
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddmm");
		String orderSeq = "user" + requestOrder.getUserSeq() + dateFormat.format(date);
		requestOrder.setId(orderSeq);
		
//		Map<String, String> resultMap = new HashMap<>();
//		resultMap.put("messageType", "success");
//        resultMap.put("message", "DB에 저장되었습니다.");
//		
//        if(orderMapper.insertOrder(requestOrder) == 0) {
//        	resultMap.put("messageType", "failure");
//            resultMap.put("message", "order DB 저장에 실패했습니다.");
//            return resultMap;
//        }
        
		orderMapper.insertOrder(requestOrder); // order 테이블 등록
		for(OrderDetail orderDetail : requestOrder.getOrders()) { // order_detail 테이블 등록
			orderDetail.setOrderSeq(orderSeq);
//			if(orderMapper.insertOrderDetail(orderDetail) == 0) {
//				resultMap.put("messageType", "failure");
//	            resultMap.put("message", "order_detail DB 저장에 실패했습니다.");
//	            return resultMap;
//			}
			orderMapper.insertOrderDetail(orderDetail);
		}
		
		
		// 장바구니 제거
		for(OrderDetail orderDetail : requestOrder.getOrders()) {
			Cart cart = new Cart();
			cart.setUserSeq(requestOrder.getUserSeq());
			cart.setProductSeq(orderDetail.getProductSeq());
			cartMapper.deleteOrder(cart);
		}
		
	}
	
	
	/* TODO 유효성 검사
	 * @ 장바구니 제거 전 DB 등록 확인
	 * @ 장바구니 제거 후 확인
	 * @ => 둘다 for문에서 유효성 검사 방법 알아본 뒤 구현
	 */
	
	
	
	
}
