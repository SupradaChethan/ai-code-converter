package com.ai.aicc.service;

import com.ai.aicc.model.ConversionRequest;
import com.ai.aicc.model.ConversionResponse;
import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.*;
import com.azure.core.credential.AzureKeyCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for converting code between different programming languages using Azure OpenAI.
 *
 * <p>This service integrates with Azure OpenAI's GPT models to perform intelligent code conversion
 * between supported languages (Java, Python, SQL). It handles the entire conversion workflow including:
 * <ul>
 *   <li>Building appropriate prompts for the AI model</li>
 *   <li>Managing Azure OpenAI client connections</li>
 *   <li>Processing conversion requests and responses</li>
 *   <li>Error handling and logging</li>
 * </ul>
 *
 * <p><b>Configuration:</b> Requires the following properties in application.properties:
 * <ul>
 *   <li>azure.openai.endpoint - Your Azure OpenAI resource endpoint</li>
 *   <li>azure.openai.api-key - Your Azure OpenAI API key</li>
 *   <li>azure.openai.deployment-name - The name of your deployed model</li>
 * </ul>
 *
 * <p><b>Usage Example:</b>
 * <pre>
 * ConversionRequest request = new ConversionRequest("SELECT * FROM users", "SQL", "Java");
 * ConversionResponse response = conversionService.convertCode(request);
 * </pre>
 *
 * @author AI Code Converter Team
 * @version 1.0
 * @see ConversionRequest
 * @see ConversionResponse
 */
@Service
public class CodeConversionService {

    /** Logger for tracking conversion operations and debugging */
    private static final Logger logger = LoggerFactory.getLogger(CodeConversionService.class);

    /**
     * Azure OpenAI endpoint URL.
     * Format: https://YOUR-RESOURCE-NAME.openai.azure.com/
     * Injected from application.properties: azure.openai.endpoint
     */
    @Value("${azure.openai.endpoint}")
    private String endpoint;

    /**
     * Azure OpenAI API key for authentication.
     * This is a sensitive credential - ensure it's not exposed in logs or version control.
     * Injected from application.properties: azure.openai.api-key
     */
    @Value("${azure.openai.api-key}")
    private String apiKey;

    /**
     * Name of the deployed model in Azure OpenAI Studio.
     * Common values: gpt-4, gpt-35-turbo, gpt-4-32k
     * Injected from application.properties: azure.openai.deployment-name
     */
    @Value("${azure.openai.deployment-name}")
    private String deploymentName;

    /**
     * Singleton instance of the Azure OpenAI client.
     * Lazily initialized to optimize resource usage.
     * Reused across multiple conversion requests to avoid connection overhead.
     */
    private OpenAIClient client;

    /**
     * Gets or creates the Azure OpenAI client instance.
     *
     * <p>This method implements lazy initialization pattern - the client is only created
     * when first needed and then reused for subsequent requests. This approach:
     * <ul>
     *   <li>Reduces startup time by deferring client creation</li>
     *   <li>Avoids unnecessary connections if service is never used</li>
     *   <li>Reuses the same client for better performance</li>
     * </ul>
     *
     * <p><b>Thread Safety:</b> This method is not thread-safe. In a multi-threaded environment,
     * consider adding synchronization or using dependency injection with @Bean.
     *
     * @return Configured Azure OpenAI client ready for API calls
     * @throws IllegalArgumentException if endpoint or API key is invalid
     */
    private OpenAIClient getClient() {
        if (client == null) {
            // Build client with endpoint and credentials from application.properties
            client = new OpenAIClientBuilder()
                    .endpoint(endpoint)
                    .credential(new AzureKeyCredential(apiKey))
                    .buildClient();
        }
        return client;
    }

    /**
     * Converts source code from one programming language to another using Azure OpenAI.
     *
     * <p>This is the main entry point for code conversion. The method:
     * <ol>
     *   <li>Logs the conversion attempt</li>
     *   <li>Builds an appropriate prompt for the AI model</li>
     *   <li>Calls Azure OpenAI API with the prompt</li>
     *   <li>Returns the converted code or error response</li>
     * </ol>
     *
     * <p><b>Supported Languages:</b> Java, Python, SQL
     *
     * <p><b>Example:</b>
     * <pre>
     * ConversionRequest request = new ConversionRequest(
     *     "def hello():\n    print('Hello')",
     *     "Python",
     *     "Java"
     * );
     * ConversionResponse response = convertCode(request);
     * </pre>
     *
     * <p><b>Error Handling:</b> All exceptions are caught and returned as error responses
     * with descriptive messages. The service never throws exceptions to the caller.
     *
     * @param request The conversion request containing source code, source language, and target language
     * @return ConversionResponse with converted code if successful, or error message if failed
     * @see ConversionRequest
     * @see ConversionResponse
     */
    public ConversionResponse convertCode(ConversionRequest request) {
        logger.info("Converting code from {} to {}", request.getSourceLanguage(), request.getTargetLanguage());

        try {
            // Build the AI prompt with conversion instructions
            String prompt = buildPrompt(request);

            // Call Azure OpenAI API to perform the conversion
            String convertedCode = callAzureOpenAI(prompt);

            logger.info("Code conversion successful");
            return new ConversionResponse(convertedCode, request.getSourceLanguage(), request.getTargetLanguage());
        } catch (Exception e) {
            // Log the error and return a user-friendly error response
            logger.error("Error converting code", e);
            return ConversionResponse.error("Failed to convert code: " + e.getMessage());
        }
    }

    /**
     * Builds a structured prompt for the Azure OpenAI API.
     *
     * <p>The prompt is carefully crafted to:
     * <ul>
     *   <li>Clearly specify the source and target languages</li>
     *   <li>Request only code output (no explanations or markdown)</li>
     *   <li>Provide the source code to be converted</li>
     * </ul>
     *
     * <p><b>Prompt Structure:</b>
     * <pre>
     * Convert the following [SOURCE_LANG] code to [TARGET_LANG].
     * Only return the converted code without explanations:
     *
     * [SOURCE_CODE]
     * </pre>
     *
     * <p><b>Design Rationale:</b> The instruction "Only return the converted code without explanations"
     * is critical to ensure the AI returns clean, executable code rather than markdown-wrapped
     * code with commentary.
     *
     * @param request The conversion request containing source language, target language, and code
     * @return A formatted prompt string ready for the AI model
     */
    private String buildPrompt(ConversionRequest request) {
        return String.format(
                "Convert the following %s code to %s. Only return the converted code without explanations:\n\n%s",
                request.getSourceLanguage(),
                request.getTargetLanguage(),
                request.getSourceCode()
        );
    }

    /**
     * Calls Azure OpenAI API to perform the actual code conversion.
     *
     * <p>This method configures and executes a chat completion request with optimized parameters
     * for code conversion tasks.
     *
     * <p><b>Message Structure:</b>
     * <ul>
     *   <li><b>System Message:</b> Sets the AI's role as a code conversion expert</li>
     *   <li><b>User Message:</b> Contains the actual conversion prompt with source code</li>
     * </ul>
     *
     * <p><b>Configuration Parameters:</b>
     * <ul>
     *   <li><b>maxTokens (2000):</b> Sufficient for most code conversions including classes.
     *       Increase if converting very large files.</li>
     *   <li><b>temperature (0.3):</b> Low temperature for deterministic, consistent output.
     *       Higher values (0.7-1.0) produce more creative but less predictable results.</li>
     * </ul>
     *
     * <p><b>Response Handling:</b> Extracts the first choice from the completion response.
     * Azure OpenAI typically returns one choice for code generation tasks.
     *
     * <p><b>Cost Considerations:</b> Each call consumes tokens based on input (prompt) and
     * output (converted code) length. Monitor usage in Azure Portal.
     *
     * @param prompt The formatted prompt instructing the AI what to convert
     * @return The converted code as a string
     * @throws RuntimeException if Azure OpenAI returns no response or connection fails
     * @throws com.azure.core.exception.HttpResponseException if API credentials are invalid
     */
    private String callAzureOpenAI(String prompt) {
        // Prepare the conversation messages
        List<ChatRequestMessage> messages = new ArrayList<>();

        // System message: Defines the AI's role and behavior
        messages.add(new ChatRequestSystemMessage(
                "You are a code conversion expert. Convert code accurately between different programming languages."
        ));

        // User message: Contains the actual conversion request
        messages.add(new ChatRequestUserMessage(prompt));

        // Configure the chat completion options
        ChatCompletionsOptions options = new ChatCompletionsOptions(messages);

        // Max tokens: Maximum length of the response (adjust for larger code files)
        options.setMaxTokens(2000);

        // Temperature: Controls randomness (0.0 = deterministic, 1.0 = creative)
        // Lower temperature (0.3) ensures consistent, reliable code conversion
        options.setTemperature(0.3);

        // Execute the API call
        ChatCompletions completions = getClient().getChatCompletions(deploymentName, options);

        // Extract and return the converted code from the first choice
        if (completions.getChoices() != null && !completions.getChoices().isEmpty()) {
            return completions.getChoices().get(0).getMessage().getContent();
        }

        // Throw exception if no response received (unlikely but possible)
        throw new RuntimeException("No response from Azure OpenAI");
    }
}
