package com.ai.aicc.controller;

import com.ai.aicc.model.ConversionRequest;
import com.ai.aicc.model.ConversionResponse;
import com.ai.aicc.service.CodeConversionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CodeConversionController.class)
class CodeConversionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CodeConversionService conversionService;

    @Test
    void testConvertCodeSuccess() throws Exception {
        ConversionResponse response = new ConversionResponse("public class Test {}", "Java", "Python");

        when(conversionService.convertCode(any(ConversionRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sourceCode\":\"SELECT * FROM users\",\"sourceLanguage\":\"SQL\",\"targetLanguage\":\"Java\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.convertedCode").exists());
    }

    @Test
    void testConvertCodeEmptySource() throws Exception {
        mockMvc.perform(post("/api/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sourceCode\":\"\",\"sourceLanguage\":\"SQL\",\"targetLanguage\":\"Java\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Source code cannot be empty"));
    }

    @Test
    void testConvertCodeServiceError() throws Exception {
        when(conversionService.convertCode(any(ConversionRequest.class)))
                .thenReturn(ConversionResponse.error("Service error"));

        mockMvc.perform(post("/api/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sourceCode\":\"SELECT * FROM users\",\"sourceLanguage\":\"SQL\",\"targetLanguage\":\"Java\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("Service error"));
    }
}
