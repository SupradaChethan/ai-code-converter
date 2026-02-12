package com.ai.aicc.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Request model for code conversion operations.
 *
 * <p>This class encapsulates the data needed to convert code from one programming
 * language to another. All fields are required for a successful conversion.
 *
 * @author AI Code Converter Team
 * @version 1.0
 */
@Schema(description = "Request payload for converting code between programming languages")
public class ConversionRequest {

    @Schema(
            description = "The source code to be converted. Can be a single line, method, class, or entire file.",
            example = "SELECT * FROM users WHERE age > 18"
    )
    private String sourceCode;

    @Schema(
            description = "The programming language of the source code",
            example = "SQL",
            allowableValues = {"Java", "Python", "SQL"}
    )
    private String sourceLanguage;

    @Schema(
            description = "The target programming language to convert to",
            example = "Java",
            allowableValues = {"Java", "Python", "SQL"}
    )
    private String targetLanguage;

    public ConversionRequest() {
    }

    public ConversionRequest(String sourceCode, String sourceLanguage, String targetLanguage) {
        this.sourceCode = sourceCode;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }
}
