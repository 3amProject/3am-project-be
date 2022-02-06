package com.tam.threeam.controller;

import java.util.HashMap;
import java.util.Map;

import com.tam.threeam.model.User;
import com.tam.threeam.response.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tam.threeam.model.Order;
import com.tam.threeam.service.OrderService;
import com.tam.threeam.service.UserService;

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
 * @ 2022/01/21		전예지			주문 처리 API 작성
 * @ 2022/01/23  	이동은			ResponseDto 적용 샘플 반영
 * @ 2022/1/25		전예지			url 수정
 */
@Controller
public class OrderController {

	@Autowired
	private OrderService orderServiceImpl;
	
	@Autowired
	private UserService userServiceImpl;
	
	/* TODO
	 * 주문자 정보 (이름, 주소, 전화번호)
	 * 배송 상품 정보 (상품명, 가격, 수량, 총 수량, 총 가격, 배송일자)
	 * 결제 정보
	 * */
	
	// 8.주문 페이지 조회 : 주문 정보, 주문자 정보 담아서 넘겨줌
	@ResponseBody
	@GetMapping("/auth/order")
	public ResponseDto orderPage(Order requestOrder) {

		// TODO 주문자 정보 응답 로직 수정 : service 단에서 구현 예정
		
//		User user = userServiceImpl.findUser(userSeq);

		Map<String, String> resultMap =  new HashMap<>();
		resultMap.put("message", "조회 성공");
//		if(user.getUserId() == null) {
//			resultMap.put("messageType", "Failure");
//			resultMap.put("message", "사용자 정보를 찾을 수 없습니다.");
//
//			return ResponseDto.sendMessage(resultMap);
//		}

		Map<String, Object> data =  new HashMap<>();
		data.put("orderList", orderServiceImpl.getProductInfo(requestOrder.getOrders()));
//		data.put("userInfo", user);



		return ResponseDto.sendData(resultMap, data);
	}
	
	
	// 9.주문페이지에서 결제하기 버튼 클릭 시 주문 처리
	// @ param : view에서 전송한 정보 전달받는 requestOrder
	// TODO 리턴 타입 변경
	@ResponseBody
	@PostMapping("/auth/order/pay")
	public String order(@RequestBody Order requestOrder) {
		orderServiceImpl.order(requestOrder);
		return "/";
	}

	
	
	
}
