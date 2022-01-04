package com.tam.threeam.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tam.threeam.model.User;

/**
 * @author 전예지
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2021/12/30
 * @
 * @ 수정일       수정자                   수정내용
 * @ ———    ————    —————————————
 * @ 2021/12/30     전예지       최초 작성
 */
@SpringBootTest
class SecurityConfigTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext context;
	
	@Spy
	private BCryptPasswordEncoder passwordEncoder;
	
	@Test
	@DisplayName("security 로그인 확인")
	public void loginTest() throws Exception{
		// given
		User user = new User();
		user.setUserId("abcd");
		String rawPassword = "1234";
		user.setPassword("1234");
		String encPassword = passwordEncoder.encode(rawPassword);
		
//		mvc = MockMvcBuilders
//						.webAppContextSetup(this.context)
//						.
		
		// when
		
		// then
	}

}
