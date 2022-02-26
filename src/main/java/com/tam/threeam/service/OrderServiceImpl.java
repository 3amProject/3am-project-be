package com.tam.threeam.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.BackingStoreException;

import com.tam.threeam.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tam.threeam.config.JwtTokenUtil;
import com.tam.threeam.mapper.CartMapper;
import com.tam.threeam.mapper.OrderMapper;
import com.tam.threeam.mapper.UserMapper;
import com.tam.threeam.model.Cart;
import com.tam.threeam.model.Order;
import com.tam.threeam.model.OrderDetail;
import com.tam.threeam.response.BaseResponseDTO;
import com.tam.threeam.response.UserResponseDto;

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
@Slf4j
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
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	// 주문 상품 정보 조회
	@Override
	@Transactional
	public BaseResponseDTO getOrderInfo(){
		final Authentication authentication = jwtTokenUtil.getAuthentication();
		String currentUserId = authentication.getName();
		int currentUserSeq = userMapper.findPkByUserId(currentUserId);

		UserResponseDto.orderPageInfo orderInfo= UserResponseDto.orderPageInfo.builder()
				.cartList(cartMapper.getCartList(currentUserSeq))
				.userInfo(userMapper.findUserById(currentUserSeq).orElse(new User()))
				.build();
		
		return BaseResponseDTO.success(orderInfo);
		
	}
	
	
	// 주문 처리
	@Override
	@Transactional
	public BaseResponseDTO order(Order requestOrder) {
		/*
		 * 사용할 데이터 세팅(user 객체, order 객체)
		 * 주문 데이터 DB에 등록
		 * 장바구니 상품 정보 DB 제거
		 * */
		final Authentication authentication = jwtTokenUtil.getAuthentication();
		String currentUserId = authentication.getName();
		int currentUserSeq = userMapper.findPkByUserId(currentUserId);

		requestOrder.setUserSeq(currentUserSeq);

		// TODO 배송일 오늘날짜 비교해서 지났으면 fail 처리

		int orderTotalPrice = 0;
		
		List<OrderDetail> orders = new ArrayList<>();
		for(OrderDetail orderDetail : requestOrder.getOrderDetails()) {
			OrderDetail orderInfo = orderMapper.getProductInfo(orderDetail.getProductSeq());
			// 수량 세팅
			orderInfo.setProductQty(orderDetail.getProductQty());
			// 상품별 총 가격
			orderInfo.setTotalPrice(orderInfo.getProductPrice()*orderDetail.getProductQty());
			// List 객체 추가
			orders.add(orderInfo);
			// 주문 총 가격 추가
			orderTotalPrice += orderInfo.getTotalPrice();
		}
		requestOrder.setOrderDetails(orders);
		requestOrder.setOrderTotalPrice(orderTotalPrice);
		
		/* 주문 데이터 DB에 등록
		 * @ order 고유값(주문번호) 생성 && 저장
		 * @ 세팅된 주문 객체 DB 데이터 등록
		 */
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String orderSeq = "user" + requestOrder.getUserSeq() + dateFormat.format(date);
		requestOrder.setId(orderSeq);
		
		// order 테이블 등록
		if(orderMapper.insertOrder(requestOrder) == 0) {
			return BaseResponseDTO.fail("주문에 실패했습니다.(order DB 등록 실패)");
		}
		
		// order_detail 테이블 등록
		for(OrderDetail orderDetail : requestOrder.getOrderDetails()) {
			orderDetail.setOrderSeq(orderSeq);
			if(orderMapper.insertOrderDetail(orderDetail) == 0) {
				return BaseResponseDTO.fail("주문에 실패했습니다.(order_detail DB 등록 실패)");
			}
		}


		// 장바구니 제거
		for(OrderDetail orderDetail : requestOrder.getOrderDetails()) {
			Cart cart = new Cart();
			cart.setUserSeq(requestOrder.getUserSeq());
			cart.setProductSeq(orderDetail.getProductSeq());
			try {
				cartMapper.deleteOrder(cart.getProductSeq(), cart.getUserSeq());
			} catch (RuntimeException e){
				log.error("장바구니에서 제거 실패", e);
				return BaseResponseDTO.fail("주문완료되었으나 장바구니 정보가 남아있습니다.");
			}
		}
		
		return BaseResponseDTO.success("주문이 완료되었습니다.");
	}
}
