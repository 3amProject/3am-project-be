package com.tam.threeam.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.tam.threeam.response.ResponseDto;
import com.tam.threeam.model.Cart;
import com.tam.threeam.service.CartService;

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
 * @ 2022/01/27			전예지			장바구니 상품 수량 추가/차감
 */
@Controller
public class CartController {

	@Autowired
	private CartService cartServiceImpl;
	
	// 장바구니 담기
	@ResponseBody
	@PostMapping("/cart")
	public ResponseDto insertCart(@RequestBody Cart cart) {
		return ResponseDto.sendData(cartServiceImpl.insertCart(cart));
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
