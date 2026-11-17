package com.example.tourist;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TouristUpdateApplicationTests {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    private static final String BODY =
            "{\"name\":\"Ravi Updated\",\"email\":\"ravi.new@example.com\",\"phone\":\"111\",\"country\":\"India\",\"passportNumber\":\"P999\"}";

    private String login() throws Exception {
        MvcResult res = mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\",\"password\":\"admin123\"}"))
                .andExpect(status().isOk()).andReturn();
        return mapper.readTree(res.getResponse().getContentAsString()).get("token").asText();
    }

    @Test
    void putWithoutTokenIsRejected() throws Exception {
        mvc.perform(put("/api/tourists/1").contentType(MediaType.APPLICATION_JSON).content(BODY))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void putWithInvalidTokenIsRejected() throws Exception {
        mvc.perform(put("/api/tourists/1").header("Authorization", "Bearer not.a.token")
                        .contentType(MediaType.APPLICATION_JSON).content(BODY))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void putWithValidTokenUpdatesRecord() throws Exception {
        mvc.perform(put("/api/tourists/1").header("Authorization", "Bearer " + login())
                        .contentType(MediaType.APPLICATION_JSON).content(BODY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ravi Updated"))
                .andExpect(jsonPath("$.email").value("ravi.new@example.com"));
    }

    @Test
    void putUnknownIdReturns404() throws Exception {
        mvc.perform(put("/api/tourists/9999").header("Authorization", "Bearer " + login())
                        .contentType(MediaType.APPLICATION_JSON).content(BODY))
                .andExpect(status().isNotFound());
    }
}
