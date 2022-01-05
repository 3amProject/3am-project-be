package com.tam.threeam.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tam.threeam.model.User;
import com.tam.threeam.service.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserServiceImpl userServiceImpl;


    @Test
    @DisplayName("회원가입 테스트")
    void join() throws Exception{
        //given
        User user = User.builder()
                            .id(1)
                            .userId("donge")
                            .password("1234")
                            .name("김한수")
                            .phoneNum("010-1111-2222")
                            .address("서울시 구로구")
                            .email("abcd@gmail.com")
                            .build();

        //when
        ResultActions resultActions = this.mockMvc.perform(post("/auth/joinProc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(user))
                 );

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("email").value("test@gmail.com"))
                .andExpect(jsonPath("name").value("Test User"))
                .andExpect(jsonPath("phone").value("010-1234-5678"))
                .andExpect(jsonPath("address").value("서울"));

    }

//    @Test
//    @DisplayName("아이디 중복 확인")
//    void checkUsername() {
//        //given
//        User user = new User();
//        user.setUserId("dogdong");
//
//        Map<String, String> resultMap = new HashMap<>();
//
//        resultMap.put("messageType", count == 0 ? "success" : "failure");
//        resultMap.put("message", count == 0 ? "사용하실 수 있는 아이디입니다." : userId+"은 이미 있는 아이디입니다.");
//        //when
//        when(userServiceImpl.checkUserId("dogdong")).thenReturn(resultMap);
//
//        /* MockMvcRequestBuilders를 이용해 MockMvc에게 호출할 URL을 생성
//         * get("/guestbooks") : GET 방식으로 /guestbooks 경로를 호출하라는 의미
//         * contentType(MediaType.APPLICATION_JSON); : application/json 형식으로 api를 호출
//         * 위의 두 가지 정보를 가진 reqBuilder를 생성  */
//        RequestBuilder reqBuilder = MockMvcRequestBuilders
//                .get("/guestbooks").contentType(MediaType.APPLICATION_JSON);
//
//        /* mockMvc.perform(reqBuilder) : reqBuilder에 해당하는 URL에 대한 요청을 보냈다는 것을 의미
//         * andExpect(status().isOk()) : mockMvc에 위해 URL이 실행되고 상태코드값이 200이 나와야 한다는 것을 의미
//         * andDo(print()) : 처리 내용 출력
//         */
//        /* .andExpect(jsonPath("$.name").value("kim")) 과 같은 문장을 사용하여
//         * Json 결과에 "name":"kim"이 있을 경우에만 성공이 될 수 있도록 할 수도 있다.
//         * 이 경우 jsonPath에 대한 라이브러리가 pom.xml파일에 추가 되야 한다. */
//        mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print());
//
//        // Guestbook Mock 객체의 getGuestbooks(0)메소드가 호출했다면 검증은 성공한다.
//        verify(guestbookService).getGuestbooks(0);
//    }

//        ResultActions resultActions = this.mockMvc.perform(get("/auth/checkUsername")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(this.objectMapper.writeValueAsString(user))
//        );
//
//        // then
//        resultActions
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("email").value("test@gmail.com"))
//                .andExpect(jsonPath("name").value("Test User"))
//                .andExpect(jsonPath("phone").value("010-1234-5678"))
//                .andExpect(jsonPath("address").value("서울"));

    }