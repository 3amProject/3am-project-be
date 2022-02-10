package com.tam.threeam.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tam.threeam.config.JwtTokenUtil;
import com.tam.threeam.model.Product;
import com.tam.threeam.response.BaseResponseDTO;
import lombok.extern.slf4j.Slf4j;
import com.tam.threeam.response.Exception.ApiException;

import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tam.threeam.mapper.CartMapper;
import com.tam.threeam.mapper.UserMapper;
import com.tam.threeam.model.Cart;


/**
 * @author 전예지
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2022/01/06
 * @
 * @ 수정일               수정자            수정내용
 * @ ———  			    ————   		—————————————
 * @ 2022/01/06		   	전예지        	최초 작성
 * @ 2022/01/07		   	전예지        	장바구니 담기, 개별상품 삭제, 전체 삭제 초안
 * @ 2022/01/12			전예지			장바구니 개별 삭제/전체 삭제 조건 삽입
 * @ 2022/01/27			전예지			장바구니 상품 수량 추가/차감
 * @ 2022/01/31			전예지			장바구니 상품 수량 확인, 로그인 후 장바구니 이동
 * @ 2022/02/05		 	이동은			전체상품 조회 추가
 * @ 2022/02/10			이동은			장바구니 담기 완료
 */
@Slf4j
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	// 전체상품 조회
	@Transactional
	@Override
	public BaseResponseDTO getProductList() throws ApiException {
		return BaseResponseDTO.success(cartMapper.getProductList());
	}



	// 장바구니 담기
	@Transactional
	@Override
	public BaseResponseDTO insertCart(Cart cartList){
		final Authentication authentication = jwtTokenUtil.getAuthentication();
		String currentUserId = authentication.getName();
		int currentUserSeq = userMapper.findPkByUserId(currentUserId);

		//TODO cart 객체에 저장된 상품 정보가 유효하지 않은 경우

		//TODO accessToken에 저장된 userId가 유효하지 않은 경우

		int countDuplicated = 0;
		int countNew = 0;

		for(Cart EachCart : cartList.getCartList()) {
			Cart cart =new Cart();
			cart.setProductSeq(EachCart.getProductSeq());
			cart.setProductQty(EachCart.getProductQty());
			cart.setDeliveryDate(EachCart.getDeliveryDate());
			cart.setUserSeq(currentUserSeq);


			if (cartMapper.checkDuplicated(cart) >= 1) {
				countDuplicated += cartMapper.plusByProductSeq(cart.getProductSeq());

			} else {
				if(cartMapper.insertCart(cart) != 1) {
					return BaseResponseDTO.fail("장바구니 담기에 실패했습니다.");
				}
				countNew += 1;
			}
		}

		log.info("새로 담긴 상품 수 = {}", countNew);
		log.info("중복된 상품 수 = {}", countDuplicated);
		return BaseResponseDTO.success("장바구니 담기에 성공했습니다.");
	}


	
	// TODO 로그인 후 장바구니 이동
	@Transactional
	@Override
	public void shiftCart(int userSeq, String cartCookieId) {
		cartMapper.shiftCart(userSeq, cartCookieId);
	}
	
	
	// 장바구니 리스트
	@Transactional
	@Override
	public BaseResponseDTO getCartList(){
		final Authentication authentication = jwtTokenUtil.getAuthentication();
        String currentUserId = authentication.getName();
        int currentUserSeq = userMapper.findPkByUserId(currentUserId);
		
		// 장바구니 주문 기한 만료 상품 삭제
//		cartMapper.deleteOrderExpired(currentUserSeq);
		
		Map<String, Object> resultMap = new HashMap<>();
		
		if(cartMapper.getCartList(currentUserSeq) != null) {
			resultMap.put("cartList", cartMapper.getCartList(currentUserSeq));
		} else {
			return BaseResponseDTO.fail("장바구니 목록을 불러오지 못했습니다.");
		}
		resultMap.put("totalPriceByProduct", getTotalPrice(currentUserSeq));
		
		return BaseResponseDTO.success(resultMap);
	}
	
	
	// TODO 장바구니 개별 상품별 총 가격 : userSeq 가져오기
	@Override
	@Transactional
	public Integer getTotalPrice(int userSeq) {
		return cartMapper.getTotalPrice(userSeq);
	}
	
	
	// TODO 장바구니 상품 수량 추가
	@Transactional
	@Override
	public Map<String, String> plusQty(int id) {
		cartMapper.plusQty(id);
		Map<String, String> resultMap = new HashMap<>();
        resultMap.put("messageType", "Success");
        resultMap.put("message", "장바구니 상품 수량 추가 완료");
        return resultMap;
	}
	
	
	// TODO 장바구니 상품 수량 차감
	@Transactional
	@Override
	public Map<String, String> minusQty(int id) {
		Map<String, String> resultMap = new HashMap<>();
		if(cartMapper.checkQty(id) < 1) {
			resultMap.put("messageType", "Failure");
			resultMap.put("message", "장바구니 상품 최소 수량 1");
			return resultMap;
		}
		
		cartMapper.minusQty(id);		
		
        resultMap.put("messageType", "Success");
        resultMap.put("message", "장바구니 상품 수량 빼기 완료");
        return resultMap;
	}
	
	
	// 장바구니 상품 수량 확인
	@Transactional
	@Override
	public int checkQty(int id) {
		return cartMapper.checkQty(id);
	}
	
	
	// 장바구니 개별 상품 삭제
	@Transactional
	@Override
	public Map<String, String> deleteOne(int id){
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		UserDetails userDetails = (UserDetails)principal;
//		
//		int requestUserSeq = userMapper.findPkByUserId(userDetails.getUsername());
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("messageType", "success");
        resultMap.put("message", "해당 상품이 장바구니에서 삭제되었습니다.");
        
        Cart cart = new Cart();
        cart.setId(id);
//        cart.setUserSeq(requestUserSeq);
        
        if(cartMapper.deleteOne(cart) == 0) {
        	resultMap.put("messageType", "failure");
            resultMap.put("message", "상품 삭제에 실패했습니다.");
            return resultMap;
        }
        cartMapper.deleteOne(cart);
        return resultMap;
	}
	
	
	// 회원 장바구니 전체 삭제
	@Transactional
	@Override
	public Map<String, String> deleteAllByUserSeq(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		
		int requestUserSeq = userMapper.findPkByUserId(userDetails.getUsername());
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("messageType", "success");
        resultMap.put("message", "장바구니가 비었습니다.");
        
        if(cartMapper.deleteAllByUserSeq(requestUserSeq) == 0) {
        	resultMap.put("messageType", "failure");
            resultMap.put("message", "장바구니 비우기에 실패했습니다.");
            return resultMap;
        }
        
        cartMapper.deleteAllByUserSeq(requestUserSeq);
        return resultMap;
	}
	
	
	// 비회원 장바구니 전체 삭제
	@Transactional
	@Override
	public Map<String, String> deleteAllByCookieId(String cartCookieId){			
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("messageType", "success");
	    resultMap.put("message", "장바구니가 비었습니다.");
	        
	    if(cartMapper.deleteAllByCookieId(cartCookieId) == 0) {
	        resultMap.put("messageType", "failure");
	        resultMap.put("message", "장바구니 비우기에 실패했습니다.");
	        return resultMap;
	    }
	        
	    cartMapper.deleteAllByCookieId(cartCookieId);
	    return resultMap;
	}
	
}
