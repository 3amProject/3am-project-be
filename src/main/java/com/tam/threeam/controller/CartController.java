package com.tam.threeam.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tam.threeam.response.BaseResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.tam.threeam.response.BaseResponseDTO;
import com.tam.threeam.response.ResponseDto;
import com.tam.threeam.config.PrincipalDetail;
import com.tam.threeam.model.Cart;
import com.tam.threeam.service.CartService;
import com.tam.threeam.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 전예지
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2022/01/06
 * @
 * @ 수정일         수정자          수정내용
 * @ ———    	  ————  	 —————————————
 * @ 2022/01/06	  전예지        	최초 작성
 * @ 2022/01/07	  전예지        	장바구니 담기, 개별상품 삭제, 전체 삭제 초안
 * @ 2022/01/12	  전예지			장바구니 리스트 리턴 타입 수정
 * @ 2022/01/25	  전예지			url 수정
 * @ 2022/01/27	  전예지			장바구니 상품 수량 추가/차감, 비회원 장바구니 담기 로직 추가
 * @ 2022/01/31	  전예지			비회원 장바구니 쿠키 확인 후 상품 추가 로직 수정
 * @ 2022/02/05	  이동은			전체상품 조회(홈 화면) 추가
 * @ 2022/02/09	  이동은   		장바구니 담기, 조회 완료
 *
 */
@Slf4j
@Controller
public class CartController {

	@Autowired
	private CartService cartServiceImpl;
	
	@Autowired
	private UserService userServiceImpl;


	// 1. 상품리스트 조회
	@GetMapping("/")
	public ResponseEntity<?> main(){
		return ResponseEntity.ok(cartServiceImpl.getProductList());
	}


	// 2. 장바구니 담기
	@PostMapping("/cart")
	public ResponseEntity<?> insertCart(@RequestBody Cart cartList) {
//		BaseResponseDTO responseDTO = cartServiceImpl.insertCart(cartList);
//		if (responseDTO.getCode().equals("BD001") || responseDTO.getCode().equals("BD002")) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
//		}
//		if (responseDTO.getCode().startsWith("ER")) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
//		}

//		log.info("request : {}", request.getAttributeNames() );
//		List<Cart> cartList = (List) request.getAttribute("Cart");
//		return ResponseEntity.ok(cartServiceImpl.insertCart(cartList));

		log.info("cartList :{}", cartList);
		System.out.println(cartList);
		return ResponseEntity.ok(cartServiceImpl.insertCart(cartList));
	}

	
	// 3.장바구니 리스트 조회
	@GetMapping("/cart")
	public ResponseEntity<?> getCartList() {
		return ResponseEntity.ok(cartServiceImpl.getCartList());
	}

	
	// 4. 장바구니 상품 수량 추가
	@PutMapping("/cart/product/plus/{cartSeq}")
	public ResponseEntity<?> plusQty(@PathVariable int cartSeq) {
		log.info("productSeq: {}", cartSeq);
		return ResponseEntity.ok(cartServiceImpl.plusProductQty(cartSeq));
	}
	
	
	// 5. 장바구니 상품 수량 차감
	@PutMapping("/cart/product/minus/{cartSeq}")
	public ResponseEntity<?> minusQty(@PathVariable int cartSeq) {
		log.info("productSeq: {}", cartSeq);
		return ResponseEntity.ok(cartServiceImpl.minusProductQty(cartSeq));
	}
	
		
	// 6. 장바구니 개별 상품 삭제
	@DeleteMapping("/cart/delete/{cartSeq}")
	public ResponseEntity<?> deleteOne(@PathVariable int cartSeq) {
		return ResponseEntity.ok(cartServiceImpl.deleteOne(cartSeq));
	}
	
	// 7. 장바구니 전체 삭제
	@DeleteMapping("/cart/deleteAll")
	public ResponseEntity<?> deleteAll() {
		return ResponseEntity.ok(cartServiceImpl.deleteAllByUserSeq());
		
		
		
//		// TODO 비회원 : @RequestBody로 cart 받아야 하는가
//		if(principalDetail.getUsername() == null) {
//			Cookie cookie=WebUtils.getCookie(request, "cartCookie");
//			String cookieValue = cookie.getValue();
////			cart.setCartCookieId(cookieValue);
//			return ResponseDto.sendData(cartServiceImpl.deleteAllByCookieId(cookieValue));
//			
//			// 회원
//		} else {
//			return ResponseDto.sendData(cartServiceImpl.deleteAllByUserSeq());
//		}
		
	}
	
}
