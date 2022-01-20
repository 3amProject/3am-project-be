package com.tam.threeam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @ 2022/01/06		   전예지        최초 작성
 * @ 2022/01/07		   전예지        장바구니 담기, 개별상품 삭제, 전체 삭제
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
	@GetMapping("/cart/") // {userSeq} 
	public ResponseDto getCartList(Model model) { //param : @PathVariable int userSeq
		
		// TODO 가격 받아오기
		model.addAttribute("totalPrice", cartServiceImpl.getTotalPrice());
		
		return ResponseDto.sendData(cartServiceImpl.getCartList());
	}
	
		
	// 장바구니 개별 상품 삭제
	@ResponseBody
	@DeleteMapping("/cart/delete/{id}")
	public ResponseDto deleteOne(@PathVariable int id) {
		return ResponseDto.sendData(cartServiceImpl.deleteOne(id));
	}
	
	
	// 장바구니 전체 삭제
	@ResponseBody
	@DeleteMapping("/cart/delete/{userSeq}")
	public ResponseDto deleteAll(@PathVariable int userSeq) {
		return ResponseDto.sendData(cartServiceImpl.deleteAll());
	}
	
}
