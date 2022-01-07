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
	
}
