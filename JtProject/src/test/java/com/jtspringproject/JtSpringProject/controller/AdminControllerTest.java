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
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void returnIndex() throws Exception {
        mockMvc.perform(get("/admin/"))
                .andExpect(status().isOk())
                .andExpect(view().name("userLogin"))
                .andDo(print());
    }

    @Test
    void index() throws Exception {
        mockMvc.perform(get("/admin/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("userLogin"))
                .andDo(print());
    }

    @Test
    void adminlogin() throws Exception {
        mockMvc.perform(get("/admin/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminlogin"))
                .andDo(print());
    }

    @Test
    void adminHome() throws Exception {
        mockMvc.perform(get("/admin/Dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/login"))
                .andDo(print());
    }

    @Test
    void adminlog() throws Exception {
        mockMvc.perform(get("/admin/loginvalidate"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminlogin"))
                .andDo(print());
    }

    @Test
    void testAdminlogin() throws Exception {
        mockMvc.perform(post("/admin/loginvalidate")
                        .param("username", "admin")
                        .param("password", "adminPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminHome"))
                .andDo(print());
    }

    @Test
    void displayHomepage() throws Exception {
        mockMvc.perform(get("/admin/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminHome"))
                .andDo(print());
    }

    @Test
    void getcategory() throws Exception {
        mockMvc.perform(get("/admin/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminlogin")) // Modify this based on your logic
                .andDo(print());
    }



}
