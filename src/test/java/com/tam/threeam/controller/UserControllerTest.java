package com.tam.threeam.controller;

import com.tam.threeam.model.User;
import com.tam.threeam.service.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userServiceImpl;


    @Test
    @DisplayName("회원가입 테스트")
    void join() {
        //given
        User user = new User;
        user.setUserId("dongcholes");

        //when
        userServiceImpl.join(user);

        //then
        Assertions.assertThat(user.getUserId()).isEqualTo(findUser.getUserId);

    }

    @Test
    @DisplayName("아이디 중복 확인")
    void checkUsername() {
    }
}