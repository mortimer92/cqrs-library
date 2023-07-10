package com.mortimer.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mortimer.command.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CommandApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createProductAPI() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                .content(asJsonString(new Product(1L,"Head First Design", "a brain friendly guid into design patterns", 100.00)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Head First Design"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("a brain friendly guid into design patterns"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(100.00));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
