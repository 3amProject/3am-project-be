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
 * @ 수정일           수정자       수정내용
 * @ ———    		————    —————————————
 * @ 2022/01/06		전예지       최초 작성
 * @ 2022/02/05		이동은	   category1,2 필드 추가
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	private int id;
	
	private String productName;
	
	private String productPrice;
	
	private String productQty;

	//TODO 추후에 EnumType으로 변경
	private String category1;

	private String category2;
}
