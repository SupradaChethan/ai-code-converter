package com.ai.aicc.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response model for code conversion operations.
 *
 * <p>This class encapsulates the result of a code conversion request. It contains
 * either the successfully converted code or an error message if the conversion failed.
 *
 * @author AI Code Converter Team
 * @version 1.0
 */
@Schema(description = "Response payload containing converted code or error information")
public class ConversionResponse {

    @Schema(
            description = "The converted code in the target language. Null if conversion failed.",
            example = "List<User> users = userRepository.findAll();\nfor (User user : users) {\n    if (user.getAge() > 18) {\n        System.out.println(user.getName());\n    }\n}"
    )
    private String convertedCode;

    @Schema(
            description = "The source language from the original request",
            example = "SQL"
    )
    private String sourceLanguage;

    @Schema(
            description = "The target language from the original request",
            example = "Java"
    )
    private String targetLanguage;

    @Schema(
            description = "Indicates whether the conversion was successful",
            example = "true"
    )
    private boolean success;

    @Schema(
            description = "Error message if conversion failed. Null if successful.",
            example = "Failed to convert code: Invalid syntax"
    )
    private String error;

    public ConversionResponse() {
    }

    public ConversionResponse(String convertedCode, String sourceLanguage, String targetLanguage) {
        this.convertedCode = convertedCode;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        this.success = true;
    }

    public static ConversionResponse error(String error) {
        ConversionResponse response = new ConversionResponse();
        response.setSuccess(false);
        response.setError(error);
        return response;
    }

    public String getConvertedCode() {
        return convertedCode;
    }

    public void setConvertedCode(String convertedCode) {
        this.convertedCode = convertedCode;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
