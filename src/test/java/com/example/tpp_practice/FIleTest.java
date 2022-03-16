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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    public String uploadAndGetId() throws Exception {
        MockHttpServletRequestBuilder mul = multipart("http://localhost:8080/file/upload")
                .file("attachment", "22222".getBytes(StandardCharsets.UTF_8))
                .param("name", "t3.txt")
                .param("path", "/")
                .param("mode", "1")
                .with(csrf());
        var result = mockMvc.perform(mul).andReturn().getResponse().getContentAsString();
        return result.split(",")[0].split(":")[1];
    }
    @Test
    public void testSendFile() throws Exception{
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
    public void testDownloadFile() throws Exception{
        MockHttpServletRequestBuilder mul2 = multipart("http://localhost:8080/file/"
                + uploadAndGetId()).with(csrf());
        this.mockMvc
                .perform(mul2)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFile() throws Exception{
        MockHttpServletRequestBuilder mul = multipart("/file/delete/"+uploadAndGetId()).with(csrf());
        this.mockMvc.perform(mul)
                .andDo(print())
                .andExpect(status()
                        .isFound())
                .andExpect(redirectedUrl("http://localhost:8080/getFiles?path =/&mode=1&up=1"));
    }

    @Test
    public void testMkdir() throws Exception{
        MockHttpServletRequestBuilder mul = multipart("http://localhost:8080/file/mkdir")
                .param("name", "fld")
                .param("path", "/")
                .with(csrf());
        this.mockMvc.perform(mul)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/getFiles?path=/fld&mode=1&up=1"));
    }

    @Test
    public void testUpdate() throws Exception{
        MockHttpServletRequestBuilder mul = multipart("http://localhost:8080/file/update")
                .param("id", uploadAndGetId())
                .param("path", "/")
                .param("newName", "new")
                .with(csrf());
        this.mockMvc.perform(mul)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/getFiles?path=/&mode=1&up=1&attribute=redirectWithRedirectView"));
    }

}
