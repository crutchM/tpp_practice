package com.example.tpp_practice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FIleTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void sendAndDownLoadFile() throws Exception{
        MockHttpServletRequestBuilder mul = multipart("http://localhost:8080/file/upload")
                .file("attachment", "22222".getBytes(StandardCharsets.UTF_8))
                .param("name", "t3.txt")
                .param("path", "/")
                .param("mode", "1")
                .with(csrf());
        this.mockMvc.perform(mul)
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    public void downloadFile() throws Exception{

        MockHttpServletRequestBuilder mul = multipart("http://localhost:8080/file/").requestAttr("id", 4);
        this.mockMvc
                .perform(mul)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("http://localhost:8080/getFiles?path=/&mode=1&up=1"));
    }

    @Test
    public void deleteFile() throws Exception{
        MockHttpServletRequestBuilder mul = multipart("/file/35");
        this.mockMvc.perform(mul).andDo(print()).andExpect(status().isFound()).andExpect(redirectedUrl("http://localhost:8080/getFiles?path = /"));
    }

    @Test
    public void loginTest() throws Exception{
        this.mockMvc.perform(formLogin().user("asd").password("123"))
                .andDo(print())
                .andExpect(status().isFound());
    }


}
