package com.ai.aicc.service;

import com.ai.aicc.model.ConversionRequest;
import com.ai.aicc.model.ConversionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CodeConversionServiceTest {

    @Autowired
    private CodeConversionService conversionService;

    @Test
    void testBuildPrompt() {
        ConversionRequest request = new ConversionRequest(
                "SELECT * FROM users",
                "SQL",
                "Java"
        );

        assertNotNull(request.getSourceCode());
        assertEquals("SQL", request.getSourceLanguage());
        assertEquals("Java", request.getTargetLanguage());
    }

    @Test
    void testConversionRequestValidation() {
        ConversionRequest request = new ConversionRequest();
        request.setSourceCode("public class Test {}");
        request.setSourceLanguage("Java");
        request.setTargetLanguage("Python");

        assertEquals("public class Test {}", request.getSourceCode());
        assertEquals("Java", request.getSourceLanguage());
        assertEquals("Python", request.getTargetLanguage());
    }

    @Test
    void testConversionResponseSuccess() {
        ConversionResponse response = new ConversionResponse(
                "class Test:",
                "Java",
                "Python"
        );

        assertTrue(response.isSuccess());
        assertEquals("class Test:", response.getConvertedCode());
        assertEquals("Java", response.getSourceLanguage());
        assertEquals("Python", response.getTargetLanguage());
    }

    @Test
    void testConversionResponseError() {
        ConversionResponse response = ConversionResponse.error("Test error");

        assertFalse(response.isSuccess());
        assertEquals("Test error", response.getError());
    }
}
