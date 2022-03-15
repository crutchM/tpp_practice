package com.example.tpp_practice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLogin() throws Exception{
        MockHttpServletRequestBuilder mul = multipart("http://localhost:8080/login")
                .param("username", "123")
                .param("password", "123")
                .with(csrf());

        mockMvc.perform(mul)
                .andDo(print())
                .andExpect(redirectedUrl("/getFiles?path=/&mode=1&up=1"));
    }

//    @Test
//    public void testReg() throws Exception{
//
//        MockHttpServletRequestBuilder mul = multipart("http://localhost:8080/reg")
//                .param("username", "1234")
//                .param("password", "1234")
//                .with(csrf());
//
//        mockMvc.perform(mul)
//                .andDo(print())
//                .andExpect(redirectedUrl("/getFiles?path=/&mode=1&up=1"));
//    }
}
