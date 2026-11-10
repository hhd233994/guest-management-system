package com.example.demo;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class DemoApplicationTests {
    @Autowired 
    private  MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test 
    void helloshouldReturnText() throws Exception{
        mockMvc.perform(get("/hello"))
        .andExpect(status().isOk())
        .andExpect(content().string("Hello Spring Boot"));
    }
    @Test
    void statusShouldReturnText() throws Exception {
        mockMvc.perform(get("/status"))
        .andExpect(status().isOk())
        .andExpect(content().string("服务正常"));
    }
    @Test
    void greetShouldReturnName() throws Exception {
        mockMvc.perform(get("/greet").param("name","小明"))
        .andExpect(status().isOk())
        .andExpect(content().string("你好, 小明"));
    }
    @Test
    void greetWithoutNameShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/greet"))
        .andExpect(status().isBadRequest());
    }
    @Test
    void doubleShouldResult() throws Exception {
        mockMvc.perform(get("/double").param("number", "5"))
        .andExpect(status().isOk())
        .andExpect(content().string("10"));
    }
    @Test 
    void postGreetShouldReadBodyName() throws Exception{
        mockMvc.perform(post("/greet")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"name\":\"小明\"}"))
               .andExpect(status().isOk())
             .andExpect(content().string("你好, 小明"));
    }
    @Test
    void addBookShouldReturnConfirmation() throws Exception{
        mockMvc.perform(post("/books")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"id\":1,\"title\":\"Java入门\",\"author\":\"张三\"}"))
               .andExpect(status().isOk())
             .andExpect(content().string("收到图书：Java入门"));
    }
    @Test 
    void getBooksShouldReturnAddedBook () throws Exception{
        mockMvc.perform(post("/books")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"id\":2,\"title\":\"查询测试书\",\"author\":\"李四\"}"))
               .andExpect(status().isOk());

               mockMvc.perform(get("/books"))
               .andExpect(status().isOk())
               .andExpect(content().string(
                org.hamcrest.Matchers.containsString("\"title\":\"查询测试书\"")));
    }
    @Test 
    void searchBooksShouldReturnMatchingBook () throws Exception{
        mockMvc.perform(post("/books")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"id\":5,\"title\":\"老人与海\",\"author\":\"海明威\"}"))
               .andExpect(status().isOk());

               mockMvc.perform(get("/books/search").param("author","海明威"))
               .andExpect(status().isOk())
               .andExpect(content().string(
                org.hamcrest.Matchers.containsString("\"title\":\"老人与海\"")));
    }
    
    @Test 
    void updateBookShouldChangeTitle() throws Exception{
        mockMvc.perform(post("/books")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"id\":3,\"title\":\"更新前的书\",\"author\":\"李四\"}"))
               .andExpect(status().isOk());

               mockMvc.perform(put("/books/3")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"id\":3,\"title\":\"更新后的书\",\"author\":\"李四\"}"))
               .andExpect(status().isOk());

               mockMvc.perform(get("/books"))
               .andExpect(status().isOk())
               .andExpect(content().string(
                org.hamcrest.Matchers.containsString("\"title\":\"更新后的书\"")));
    }

    @Test 
    void deleteBookShouldRemoveBook() throws Exception{
        mockMvc.perform(post("/books")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"id\":4,\"title\":\"待删除的书\",\"author\":\"王五\"}"))
               .andExpect(status().isOk());

               mockMvc.perform(delete("/books/4"))
               .andExpect(status().isOk());

               mockMvc.perform(get("/books"))
               .andExpect(status().isOk())
               .andExpect(content().string(
                org.hamcrest.Matchers.not(
                    org.hamcrest.Matchers.containsString("\"title\":\"待删除的书\""))));
    }


}