package com.ai.aicc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI/Swagger configuration for the AI Code Converter API.
 *
 * <p>This configuration provides interactive API documentation accessible via Swagger UI.
 * The documentation includes all REST endpoints, request/response models, and example payloads.
 *
 * <p><b>Access Points:</b>
 * <ul>
 *   <li>Swagger UI: <a href="http://localhost:8080/ai-code-converter/swagger-ui.html">
 *       http://localhost:8080/ai-code-converter/swagger-ui.html</a></li>
 *   <li>OpenAPI JSON: <a href="http://localhost:8080/ai-code-converter/v3/api-docs">
 *       http://localhost:8080/ai-code-converter/v3/api-docs</a></li>
 * </ul>
 *
 * <p><b>Features:</b>
 * <ul>
 *   <li>Interactive API testing directly from the browser</li>
 *   <li>Automatic request/response schema generation</li>
 *   <li>Example payloads for all endpoints</li>
 *   <li>Authentication and error response documentation</li>
 * </ul>
 *
 * @author AI Code Converter Team
 * @version 1.0
 * @see <a href="https://springdoc.org/">SpringDoc Documentation</a>
 */
@Configuration
public class OpenApiConfig {

    /**
     * Server port from application.properties.
     * Used to construct the correct API server URL in documentation.
     */
    @Value("${server.port:8080}")
    private String serverPort;

    /**
     * Context path from application.properties.
     * Used to construct the correct API server URL in documentation.
     */
    @Value("${server.servlet.context-path:/ai-code-converter}")
    private String contextPath;

    /**
     * Configures the OpenAPI documentation metadata.
     *
     * <p>This bean defines:
     * <ul>
     *   <li>API title, description, and version</li>
     *   <li>Contact information for API support</li>
     *   <li>License information</li>
     *   <li>Server URLs for API testing</li>
     * </ul>
     *
     * <p><b>Customization:</b> Modify the returned OpenAPI object to add:
     * <ul>
     *   <li>Security schemes (API keys, OAuth, etc.)</li>
     *   <li>Additional servers (staging, production)</li>
     *   <li>External documentation links</li>
     *   <li>Tags for endpoint grouping</li>
     * </ul>
     *
     * @return Configured OpenAPI object with API metadata
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AI Code Converter API")
                        .version("1.0.0")
                        .description("""
                                ## AI-Powered Code Conversion Service

                                Convert code between different programming languages using Azure OpenAI.

                                ### Supported Conversions:
                                - **SQL ↔ Java** - Convert SQL queries to Java and vice versa
                                - **Java ↔ Python** - Convert Java code to Python and vice versa
                                - **SQL ↔ Python** - Convert SQL queries to Python and vice versa
                                - **.NET (C#) ↔ Java** - Convert C# code to Java and vice versa
                                - **.NET (C#) ↔ Python** - Convert C# code to Python and vice versa
                                - **.NET (C#) ↔ SQL** - Convert C# data access to SQL and vice versa

                                ### Supported Languages:
                                - **Java** - Convert to/from Java code
                                - **Python** - Convert to/from Python code
                                - **SQL** - Convert to/from SQL queries
                                - **.NET (C#)** - Convert to/from C# code

                                ### How to Use:
                                1. Select your source and target languages
                                2. Paste your source code
                                3. Submit the conversion request
                                4. Receive production-ready converted code

                                ### Technology Stack:
                                - **Backend:** Java 17 + Spring Boot 3.2
                                - **AI Engine:** Azure OpenAI (GPT-4)
                                - **Logging:** Logback with file and console output

                                ### Rate Limits:
                                API usage is subject to Azure OpenAI rate limits configured in your deployment.
                                Monitor usage in Azure Portal to avoid throttling.
                                """)
                        .contact(new Contact()
                                .name("AI Code Converter Team")
                                .email("support@aicc.example.com")
                                .url("https://github.com/your-org/ai-code-converter"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort + contextPath)
                                .description("Local Development Server")
                ));
    }
}
