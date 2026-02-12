package com.ai.aicc.controller;

import com.ai.aicc.model.ConversionRequest;
import com.ai.aicc.model.ConversionResponse;
import com.ai.aicc.service.CodeConversionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for code conversion operations.
 *
 * <p>This controller exposes the main API endpoint for converting code between different
 * programming languages using Azure OpenAI. It handles HTTP requests, validates input,
 * and returns conversion results or error responses.
 *
 * <p><b>Endpoint:</b> POST /api/convert
 *
 * @author AI Code Converter Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/convert")
@CrossOrigin(origins = "*")
@Tag(name = "Code Conversion", description = "API endpoints for converting code between programming languages")
public class CodeConversionController {
    private static final Logger logger = LoggerFactory.getLogger(CodeConversionController.class);

    @Autowired
    private CodeConversionService conversionService;

    @Operation(
            summary = "Convert code between programming languages",
            description = """
                    Converts source code from one programming language to another using Azure OpenAI.

                    **Supported Languages:** Java, Python, SQL

                    **Example Use Cases:**
                    - Convert SQL queries to Java JDBC code
                    - Convert Java methods to Python functions
                    - Convert Python scripts to Java applications

                    **Processing Time:** Typically 2-10 seconds depending on code complexity and Azure OpenAI response time.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Code converted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ConversionResponse.class),
                            examples = @ExampleObject(
                                    name = "SQL to Java Conversion",
                                    value = """
                                            {
                                              "convertedCode": "String sql = \\"SELECT * FROM users WHERE id = ?\\";\nPreparedStatement stmt = conn.prepareStatement(sql);\nstmt.setInt(1, userId);",
                                              "sourceLanguage": "SQL",
                                              "targetLanguage": "Java",
                                              "success": true,
                                              "error": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request - source code is empty or missing",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ConversionResponse.class),
                            examples = @ExampleObject(
                                    name = "Empty Source Code Error",
                                    value = """
                                            {
                                              "convertedCode": null,
                                              "sourceLanguage": null,
                                              "targetLanguage": null,
                                              "success": false,
                                              "error": "Source code cannot be empty"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error - Azure OpenAI API failure or service error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ConversionResponse.class),
                            examples = @ExampleObject(
                                    name = "Service Error",
                                    value = """
                                            {
                                              "convertedCode": null,
                                              "sourceLanguage": "SQL",
                                              "targetLanguage": "Java",
                                              "success": false,
                                              "error": "Failed to convert code: Connection timeout"
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ConversionResponse> convertCode(
            @Parameter(
                    description = "Conversion request containing source code, source language, and target language",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ConversionRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "SQL to Java",
                                            value = """
                                                    {
                                                      "sourceCode": "SELECT * FROM users WHERE id = 1",
                                                      "sourceLanguage": "SQL",
                                                      "targetLanguage": "Java"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Python to Java",
                                            value = """
                                                    {
                                                      "sourceCode": "def hello():\\n    print('Hello World')",
                                                      "sourceLanguage": "Python",
                                                      "targetLanguage": "Java"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Java to Python",
                                            value = """
                                                    {
                                                      "sourceCode": "public class Calculator {\\n    public int add(int a, int b) {\\n        return a + b;\\n    }\\n}",
                                                      "sourceLanguage": "Java",
                                                      "targetLanguage": "Python"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            @RequestBody ConversionRequest request) {
        logger.info("Received conversion request: {} to {}", request.getSourceLanguage(), request.getTargetLanguage());

        if (request.getSourceCode() == null || request.getSourceCode().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ConversionResponse.error("Source code cannot be empty"));
        }

        ConversionResponse response = conversionService.convertCode(request);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
