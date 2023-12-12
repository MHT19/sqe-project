package com.jtspringproject.JtSpringProject.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registerUser() throws Exception {
        mockMvc.perform(post("/register")
                        .param("username", "testuser")
                        .param("password", "testpassword")
                        .param("email", "testuser@example.com")
                        .param("address", "Test Address"))
                .andExpect(status().isOk())
                .andExpect(view().name("userLogin"))
                .andDo(print());
    }

    @Test
    void buy() throws Exception {
        mockMvc.perform(post("/buy")
                        .param("productId", "1")
                        .param("quantity", "2"))
                .andExpect(status().isOk())
                .andExpect(view().name("orderConfirmation"))
                .andDo(print());
    }

    @Test
    void userlogin() throws Exception {
        mockMvc.perform(get("/user/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("userLogin"))
                .andDo(print());
    }

    @Test
    void userLogout() throws Exception {
        mockMvc.perform(get("/user/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andDo(print());
    }

    @Test
    void cartproduct() throws Exception {
        mockMvc.perform(get("/user/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andDo(print());
    }

    @Test
    void testUserlogin() throws Exception {
        mockMvc.perform(post("/user/login")
                        .param("username", "testuser")
                        .param("password", "testpassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("userHome"))
                .andDo(print());
    }

    @Test
    void getproduct() throws Exception {
        mockMvc.perform(get("/user/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andDo(print());
    }

}
