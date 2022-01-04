package com.tam.threeam.controller;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    private MockMvc mvc;

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
        Gson gson = new Gson();
        String content = gson.toJson(user);

        //when
        mvc.perform(post("/auth/joinProc"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .characterEncoding("UTF-8")
                .andExpect(status().isCreated());

        // then
        actions .andExpect(status().isCreated())
                .andExpect(jsonPath("email").value("test@gmail.com"))
                .andExpect(jsonPath("name").value("Test User"))
                .andExpect(jsonPath("phone").value("010-1234-5678"))
                .andExpect(jsonPath("address").value("서울"));


    }

    @Test
    @DisplayName("아이디 중복 확인")
    void checkUsername() {
    }
}