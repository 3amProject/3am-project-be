package com.tam.threeam.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.tam.threeam.response.ResponseDto;
import com.tam.threeam.config.auth.PrincipalDetail;
import com.tam.threeam.model.Cart;
import com.tam.threeam.service.CartService;
import com.tam.threeam.service.CartServiceImpl;
import com.tam.threeam.service.UserService;

/**
 * @author 전예지
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2022/01/06
 * @
 * @ 수정일         수정자                   수정내용
 * @ ———    ————    —————————————
 * @ 2022/01/06			전예지        	최초 작성
 * @ 2022/01/07			전예지        	장바구니 담기, 개별상품 삭제, 전체 삭제
 * @ 2022/01/12			전예지			장바구니 리스트 리턴 타입 수정
 * @ 2022/01/25			전예지			url 수정
 * @ 2022/01/27			전예지			장바구니 상품 수량 추가/차감, 비회원 장바구니 담기 로직 추가
 * @ 2022/01/31			전예지			비회원 장바구니 쿠키 확인 후 상품 추가 로직 수정
 */
@Controller
public class CartController {

	@Autowired
	private CartService cartServiceImpl;
	
	@Autowired
	private UserService userServiceImpl;
	
	// TODO 리턴값 체크
	// 장바구니 담기
	@ResponseBody
	@PostMapping("/cart")
	public ResponseDto insertCart(@RequestBody Cart cart, @AuthenticationPrincipal PrincipalDetail principalDetail, HttpServletRequest request, HttpServletResponse response) {
		// 요청값에서 "cartCookie"라는 key값의 쿠키 가져오기
		Cookie cookie = WebUtils.getCookie(request, "cartCookie");
		
		// 비회원 장바구니 담기 버튼 첫 클릭 시 쿠키 생성
		if(principalDetail.getUsername() == null && cookie == null) {
			// 랜덤 문자열로 쿠키 생성
			String cookieId = RandomStringUtils.random(10, true, true);
			Cookie cartCookie = new Cookie("cartCookie", cookieId);
			cartCookie.setPath("/"); // "/" url 하위 경로에 대해서만 쿠키 전송
			cartCookie.setMaxAge(60 * 60 * 24 * 1); // 쿠키 유효기간 : 1일
			response.addCookie(cartCookie); // 클라이언트에 쿠키 전송
			cart.setCartCookieId(cookieId);
			cartServiceImpl.insertCart(cart);
			
			// 비회원 장바구니 쿠키 확인 후 상품 추가
		}	else if (principalDetail.getUsername() == null && cookie != null) {
			String cookieValue = cookie.getValue();
			cart.setCartCookieId(cookieValue);
			
			// 장바구니 상품 중복 확인 후 상품 수량 더하기
			if(cartServiceImpl.checkQty(cart.getId()) != 0) {
				cartServiceImpl.plusQty(cart.getId());
			}
			
			// 쿠키 재설정 후 전송
			cookie.setPath("/");
			cookie.setMaxAge(60 * 60 * 24 * 1);
			response.addCookie(cookie);
			
			cartServiceImpl.insertCart(cart);
			
			// 회원 장바구니 상품 추가
		}	else if (principalDetail.getUsername() != null) {
			int userSeq = userServiceImpl.findUserPk(principalDetail.getUsername());
			cart.setUserSeq(userSeq);
			cartServiceImpl.insertCart(cart);
		}
		
		return ResponseDto.sendMessage(cartServiceImpl.insertCart(cart));
	}
	
	
	// TODO 장바구니 리스트
	@ResponseBody
	@GetMapping("/cart")
	public ResponseDto getCartList() {	
		Map<String, Object> resultMap =  new HashMap<>();
		resultMap.put("cartList", cartServiceImpl.getCartList());
		resultMap.put("totalPriceByProduct", cartServiceImpl.getTotalPrice());
		
		return ResponseDto.sendData(resultMap);
	}

	
	// TODO 장바구니 상품 수량 추가
	@ResponseBody
	@PutMapping("/cart/product/plus")
	public ResponseDto plusQty(@RequestParam int id) {
		return ResponseDto.sendMessage(cartServiceImpl.plusQty(id));
	}
	
	
	// TODO 장바구니 상품 수량 차감
	@ResponseBody
	@PutMapping("/cart/product/minus")
	public ResponseDto minusQty(@RequestParam int id) {
		return ResponseDto.sendMessage(cartServiceImpl.minusQty(id));
	}
	
		
	// 장바구니 개별 상품 삭제
	@ResponseBody
	@DeleteMapping("/cart/delete/{id}")
	public ResponseDto deleteOne(@PathVariable int id) {
		return ResponseDto.sendData(cartServiceImpl.deleteOne(id));
	}
	
	
	// 장바구니 전체 삭제
	@ResponseBody
	@DeleteMapping("/cart/deleteAll")
	public ResponseDto deleteAll() {
		return ResponseDto.sendData(cartServiceImpl.deleteAll());
	}
	
}
