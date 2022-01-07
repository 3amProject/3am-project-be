package com.tam.threeam.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
 * @ 2022/01/07		   전예지        property 추가
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

	private int id;

	private int userSeq;
	
	private int productSeq;
	
	private int productQty;
	
	private int price;
	
	private int totalPrice;
	
	private Product product;
	
}