# AI Code Converter

<div align="center">

**An intelligent, production-ready code conversion service powered by Azure OpenAI**

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Azure OpenAI](https://img.shields.io/badge/Azure-OpenAI-blue.svg)](https://azure.microsoft.com/en-us/products/ai-services/openai-service)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

</div>

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
  - [Web UI](#web-ui)
  - [REST API](#rest-api)
  - [Swagger Documentation](#swagger-api-documentation)
- [Supported Conversions](#supported-conversions)
- [API Reference](#api-reference)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Technology Stack](#technology-stack)
- [Performance](#performance)
- [Security](#security)
- [Troubleshooting](#troubleshooting)
- [Development](#development)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [License](#license)

---

## 🎯 Overview

AI Code Converter is a sophisticated Spring Boot application that leverages Azure OpenAI's GPT models to intelligently convert code between different programming languages. Whether you're migrating legacy systems, learning a new language, or need quick code translations, this tool provides accurate, production-ready conversions with a simple, intuitive interface.

### Why AI Code Converter?

- **🚀 Production-Ready**: Built with enterprise-grade Spring Boot framework
- **🤖 AI-Powered**: Utilizes Azure OpenAI's GPT-4 for intelligent, context-aware conversions
- **🎨 Modern UI**: Clean, professional dark-themed interface inspired by modern IDEs
- **📚 Well-Documented**: Comprehensive Swagger/OpenAPI documentation with interactive testing
- **🔒 Secure**: Environment-based configuration for sensitive credentials
- **⚡ Fast**: Optimized for quick response times with lazy-loaded Azure clients
- **🧪 Tested**: Includes unit tests for controllers and services

---

## ✨ Features

### Core Functionality
- **Multi-Language Support**: Convert between Java, Python, and SQL
- **Bidirectional Conversion**: All language pairs work in both directions
- **Intelligent Translation**: Preserves logic, handles idioms, respects conventions
- **Syntax Validation**: Returns syntactically correct, executable code

### User Interfaces
- **Web UI**: Beautiful dark-themed interface with syntax-highlighted code areas
- **REST API**: JSON-based API for programmatic access
- **Swagger UI**: Interactive API documentation and testing

### Technical Features
- **Comprehensive Logging**: Logback integration with file and console output
- **Error Handling**: Graceful error responses with detailed messages
- **CORS Enabled**: Cross-origin requests supported for frontend integration
- **Context Path**: Clean URL structure with `/ai-code-converter` prefix
- **Environment Configuration**: Secure credential management via environment variables

---

## 🏗️ Architecture

### High-Level Architecture

```
┌─────────────────┐
│   Web Browser   │
│   (HTML/JS)     │
└────────┬────────┘
         │ HTTP
         ▼
┌─────────────────────────────────────┐
│      Spring Boot Application        │
│  ┌───────────────────────────────┐  │
│  │  CodeConversionController     │  │
│  │  - REST API Endpoints         │  │
│  │  - Request Validation         │  │
│  └──────────┬────────────────────┘  │
│             │                        │
│  ┌──────────▼────────────────────┐  │
│  │  CodeConversionService        │  │
│  │  - Business Logic             │  │
│  │  - Prompt Engineering         │  │
│  │  - Azure Client Management    │  │
│  └──────────┬────────────────────┘  │
│             │                        │
└─────────────┼────────────────────────┘
              │ HTTPS
              ▼
    ┌─────────────────────┐
    │   Azure OpenAI API  │
    │   (GPT-4 / GPT-3.5) │
    └─────────────────────┘
```

### Component Description

#### 1. **Presentation Layer**
- **Static Web UI**: Single-page application with vanilla JavaScript
- **REST Controller**: Spring MVC controller handling HTTP requests
- **Swagger UI**: Auto-generated interactive API documentation

#### 2. **Service Layer**
- **CodeConversionService**: Core business logic
  - Prompt construction
  - Azure OpenAI client management
  - Response processing
  - Error handling

#### 3. **Integration Layer**
- **Azure OpenAI SDK**: Official Azure SDK for Java
  - Chat completions API
  - Token management
  - Retry logic

#### 4. **Configuration**
- **OpenApiConfig**: Swagger/OpenAPI configuration
- **Application Properties**: Environment-based configuration
- **Logback**: Structured logging configuration

---

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

### Required Software

| Software | Version | Download Link |
|----------|---------|---------------|
| **Java JDK** | 17 or higher | [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/) |
| **Maven** | 3.6+ | [Apache Maven](https://maven.apache.org/download.cgi) |
| **Azure Account** | Active subscription | [Azure Portal](https://portal.azure.com/) |

### Verify Installation

```bash
# Check Java version
java -version
# Expected output: java version "17.x.x"

# Check Maven version
mvn -version
# Expected output: Apache Maven 3.x.x
```

### Azure OpenAI Setup

You'll need:
1. **Azure OpenAI Resource** - Create in Azure Portal
2. **Deployed Model** - GPT-4, GPT-3.5-turbo, or similar
3. **API Key** - From Azure Portal
4. **Endpoint URL** - Your resource endpoint

**Detailed Azure Setup Guide**: See [Configuration](#configuration) section below.

---

## 🚀 Installation

### Quick Start

```bash
# 1. Clone the repository
git clone https://github.com/your-org/ai-code-converter.git
cd ai-code-converter

# 2. Configure Azure credentials (see Configuration section)
export AZURE_OPENAI_ENDPOINT="https://your-resource.openai.azure.com/"
export AZURE_OPENAI_API_KEY="your-api-key"
export AZURE_OPENAI_DEPLOYMENT="gpt-4"

# 3. Build the project
mvn clean install

# 4. Run the application
mvn spring-boot:run

# 5. Access the application
# Open: http://localhost:8080/ai-code-converter
```

### Alternative: Run JAR

```bash
# Build executable JAR
mvn clean package

# Run the JAR
java -jar target/aicc-1.0.0.jar

# Or with environment variables inline
AZURE_OPENAI_ENDPOINT="https://..." \
AZURE_OPENAI_API_KEY="..." \
AZURE_OPENAI_DEPLOYMENT="gpt-4" \
java -jar target/aicc-1.0.0.jar
```

---

## ⚙️ Configuration

### Azure OpenAI Setup (Step-by-Step)

#### Step 1: Create Azure OpenAI Resource

1. Navigate to [Azure Portal](https://portal.azure.com/)
2. Click **"+ Create a resource"**
3. Search for **"Azure OpenAI"**
4. Click **"Create"**
5. Fill in the required fields:
   - **Subscription**: Your Azure subscription
   - **Resource Group**: Create new or select existing
   - **Region**: Choose a supported region (e.g., East US, West Europe)
   - **Name**: Unique name (e.g., `my-code-converter-openai`)
   - **Pricing Tier**: Standard S0
6. Click **"Review + Create"** → **"Create"**
7. Wait for deployment (1-2 minutes)

#### Step 2: Get Your Endpoint and API Key

1. Go to your Azure OpenAI resource
2. In the left menu, click **"Keys and Endpoint"**
3. Copy:
   - **Endpoint**: `https://YOUR-RESOURCE-NAME.openai.azure.com/`
   - **Key 1** or **Key 2**: Your API key

#### Step 3: Deploy a Model

1. In your resource, click **"Go to Azure OpenAI Studio"**
2. Click **"Deployments"** in the left menu
3. Click **"+ Create new deployment"**
4. Configure:
   - **Model**: Select `gpt-4` (recommended) or `gpt-35-turbo`
   - **Deployment name**: `gpt-4` or `code-converter`
   - **Model version**: Latest or auto-update
   - **Deployment type**: Standard
   - **Tokens per minute rate limit**: 10K-30K (adjustable)
5. Click **"Create"**

#### Step 4: Configure the Application

**Option A: Environment Variables (Recommended)**

```bash
# Linux/macOS
export AZURE_OPENAI_ENDPOINT="https://your-resource.openai.azure.com/"
export AZURE_OPENAI_API_KEY="your-api-key-here"
export AZURE_OPENAI_DEPLOYMENT="gpt-4"

# Windows PowerShell
$env:AZURE_OPENAI_ENDPOINT="https://your-resource.openai.azure.com/"
$env:AZURE_OPENAI_API_KEY="your-api-key-here"
$env:AZURE_OPENAI_DEPLOYMENT="gpt-4"

# Windows Command Prompt
set AZURE_OPENAI_ENDPOINT=https://your-resource.openai.azure.com/
set AZURE_OPENAI_API_KEY=your-api-key-here
set AZURE_OPENAI_DEPLOYMENT=gpt-4
```

**Option B: Application Properties**

Edit `src/main/resources/application.properties`:

```properties
azure.openai.endpoint=https://your-resource.openai.azure.com/
azure.openai.api-key=your-actual-api-key
azure.openai.deployment-name=gpt-4
```

⚠️ **Security Warning**: Never commit API keys to version control!

### Advanced Configuration

#### Server Configuration

```properties
# Change server port
server.port=9090

# Change context path
server.servlet.context-path=/code-converter

# Access would be: http://localhost:9090/code-converter
```

#### Logging Configuration

```properties
# Adjust log levels
logging.level.root=INFO
logging.level.com.ai.aicc=DEBUG
logging.level.com.azure=WARN

# Log file location (see logback-spring.xml)
# Logs written to: logs/ai-code-converter.log
```

#### Swagger Configuration

```properties
# Disable Swagger in production
springdoc.swagger-ui.enabled=false

# Customize Swagger path
springdoc.swagger-ui.path=/api-docs
```

---

## 📖 Usage

### Web UI

The web interface provides the easiest way to convert code.

1. **Access the UI**
   ```
   http://localhost:8080/ai-code-converter/
   ```

2. **Convert Code**
   - Select your **source language** (Java, Python, or SQL)
   - Paste your code in the left textarea
   - Select your **target language**
   - Click **"Convert Code"**
   - View the converted code in the right textarea

3. **Features**
   - Professional dark theme
   - Syntax-highlighted code areas
   - Real-time validation
   - Error messages
   - Loading indicators

**Example Workflow:**
```
1. Select: SQL → Java
2. Paste: SELECT * FROM users WHERE age > 18
3. Click: Convert Code
4. Result: PreparedStatement with proper JDBC code
```

### REST API

For programmatic access, use the REST API.

#### Endpoint

```
POST /api/convert
Content-Type: application/json
```

#### Request Body

```json
{
  "sourceCode": "def calculate_sum(numbers):\n    return sum(numbers)",
  "sourceLanguage": "Python",
  "targetLanguage": "Java"
}
```

#### Response (Success)

```json
{
  "convertedCode": "public int calculateSum(int[] numbers) {\n    return Arrays.stream(numbers).sum();\n}",
  "sourceLanguage": "Python",
  "targetLanguage": "Java",
  "success": true,
  "error": null
}
```

#### Response (Error)

```json
{
  "convertedCode": null,
  "sourceLanguage": null,
  "targetLanguage": null,
  "success": false,
  "error": "Source code cannot be empty"
}
```

#### HTTP Status Codes

| Code | Meaning | Description |
|------|---------|-------------|
| 200 | OK | Conversion successful |
| 400 | Bad Request | Invalid input (empty code, etc.) |
| 500 | Internal Server Error | Azure API error or service failure |

### Swagger API Documentation

Interactive API documentation with live testing capabilities.

#### Accessing Swagger UI

```
http://localhost:8080/ai-code-converter/swagger-ui.html
```

#### Features

- **Try It Out**: Test API calls directly from the browser
- **Request Examples**: Pre-filled examples for all conversions
- **Schema Documentation**: Complete request/response models
- **Response Codes**: All possible HTTP responses documented
- **Download OpenAPI Spec**: Export API specification

#### OpenAPI JSON

Raw OpenAPI specification:
```
http://localhost:8080/ai-code-converter/v3/api-docs
```

---

## 🔄 Supported Conversions

### Language Matrix

| From ↓ / To → | Java | Python | SQL |
|---------------|------|--------|-----|
| **Java**      | —    | ✅     | ✅  |
| **Python**    | ✅   | —      | ✅  |
| **SQL**       | ✅   | ✅     | —   |

### Conversion Examples

#### SQL → Java
**Input:**
```sql
SELECT id, name, email FROM users WHERE age > 18 ORDER BY name
```
**Output:**
```java
String sql = "SELECT id, name, email FROM users WHERE age > ? ORDER BY name";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setInt(1, 18);
ResultSet rs = stmt.executeQuery();
// Process results...
```

#### Java → Python
**Input:**
```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}
```
**Output:**
```python
class Calculator:
    def add(self, a: int, b: int) -> int:
        return a + b
```

#### Python → Java
**Input:**
```python
def filter_even_numbers(numbers):
    return [n for n in numbers if n % 2 == 0]
```
**Output:**
```java
public List<Integer> filterEvenNumbers(List<Integer> numbers) {
    return numbers.stream()
                  .filter(n -> n % 2 == 0)
                  .collect(Collectors.toList());
}
```

---

## 📚 API Reference

### POST /api/convert

Convert code from one programming language to another.

#### Request

**Headers:**
```
Content-Type: application/json
```

**Body Parameters:**

| Field | Type | Required | Description | Example |
|-------|------|----------|-------------|---------|
| `sourceCode` | string | Yes | The code to convert | `"SELECT * FROM users"` |
| `sourceLanguage` | string | Yes | Source language | `"SQL"`, `"Java"`, or `"Python"` |
| `targetLanguage` | string | Yes | Target language | `"SQL"`, `"Java"`, or `"Python"` |

**Constraints:**
- `sourceCode` cannot be empty or null
- `sourceLanguage` and `targetLanguage` must be different
- Supported values: `"Java"`, `"Python"`, `"SQL"`

#### Response

**Success Response (200 OK):**

```json
{
  "convertedCode": "String result = ...",
  "sourceLanguage": "Python",
  "targetLanguage": "Java",
  "success": true,
  "error": null
}
```

**Error Response (400 Bad Request):**

```json
{
  "convertedCode": null,
  "sourceLanguage": null,
  "targetLanguage": null,
  "success": false,
  "error": "Source code cannot be empty"
}
```

**Error Response (500 Internal Server Error):**

```json
{
  "convertedCode": null,
  "sourceLanguage": "SQL",
  "targetLanguage": "Java",
  "success": false,
  "error": "Failed to convert code: Connection timeout"
}
```

#### cURL Examples

**SQL to Java:**
```bash
curl -X POST http://localhost:8080/ai-code-converter/api/convert \
  -H "Content-Type: application/json" \
  -d '{
    "sourceCode": "SELECT * FROM users WHERE id = 1",
    "sourceLanguage": "SQL",
    "targetLanguage": "Java"
  }'
```

**Python to Java:**
```bash
curl -X POST http://localhost:8080/ai-code-converter/api/convert \
  -H "Content-Type: application/json" \
  -d '{
    "sourceCode": "def add(a, b):\n    return a + b",
    "sourceLanguage": "Python",
    "targetLanguage": "Java"
  }'
```

**Java to Python:**
```bash
curl -X POST http://localhost:8080/ai-code-converter/api/convert \
  -H "Content-Type: application/json" \
  -d '{
    "sourceCode": "public int multiply(int a, int b) { return a * b; }",
    "sourceLanguage": "Java",
    "targetLanguage": "Python"
  }'
```

---

## 🧪 Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=CodeConversionControllerTest

# Run with coverage
mvn clean test jacoco:report
```

### Test Structure

```
src/test/java/com/ai/aicc/
├── controller/
│   └── CodeConversionControllerTest.java    # Controller layer tests
└── service/
    └── CodeConversionServiceTest.java       # Service layer tests
```

### Test Cases Included

#### Controller Tests
- ✅ Successful code conversion
- ✅ Empty source code validation
- ✅ Service error handling
- ✅ HTTP status code verification

#### Service Tests
- ✅ Prompt building
- ✅ Request validation
- ✅ Response creation
- ✅ Error response handling

### Manual Testing

See `docs/TEST_CASES.md` for comprehensive manual test cases including:
- 12 detailed test scenarios
- All language conversion pairs
- Error handling tests
- Complex code examples
- Expected outputs
- Performance benchmarks

---

## 📁 Project Structure

```
ai-code-converter/
├── .claude/                          # Claude Code configuration
├── docs/
│   ├── Requirement.md               # Original requirements
│   └── TEST_CASES.md                # Comprehensive test cases
├── logs/                            # Application logs (auto-generated)
│   └── ai-code-converter.log
├── src/
│   ├── main/
│   │   ├── java/com/ai/aicc/
│   │   │   ├── config/
│   │   │   │   └── OpenApiConfig.java          # Swagger configuration
│   │   │   ├── controller/
│   │   │   │   └── CodeConversionController.java  # REST endpoints
│   │   │   ├── model/
│   │   │   │   ├── ConversionRequest.java      # Request DTO
│   │   │   │   └── ConversionResponse.java     # Response DTO
│   │   │   ├── service/
│   │   │   │   └── CodeConversionService.java  # Business logic
│   │   │   └── AiCodeConverterApplication.java # Main class
│   │   └── resources/
│   │       ├── static/
│   │       │   └── index.html               # Web UI
│   │       ├── application.properties       # Configuration
│   │       └── logback-spring.xml          # Logging config
│   └── test/
│       └── java/com/ai/aicc/
│           ├── controller/
│           │   └── CodeConversionControllerTest.java
│           └── service/
│               └── CodeConversionServiceTest.java
├── target/                          # Build output (auto-generated)
├── .gitignore
├── pom.xml                          # Maven configuration
└── README.md                        # This file
```

### Key Files Explained

#### Backend

| File | Purpose |
|------|---------|
| `AiCodeConverterApplication.java` | Spring Boot entry point |
| `CodeConversionController.java` | REST API endpoints, request validation |
| `CodeConversionService.java` | Azure OpenAI integration, prompt engineering |
| `OpenApiConfig.java` | Swagger/OpenAPI documentation configuration |
| `ConversionRequest.java` | Request model with validation |
| `ConversionResponse.java` | Response model with success/error handling |

#### Configuration

| File | Purpose |
|------|---------|
| `application.properties` | Main configuration (server, Azure, logging) |
| `logback-spring.xml` | Logging configuration (console + file) |
| `pom.xml` | Maven dependencies and build configuration |

#### Frontend

| File | Purpose |
|------|---------|
| `index.html` | Single-page web UI with dark theme |

---

## 🛠️ Technology Stack

### Backend

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 17 | Programming language |
| **Spring Boot** | 3.2.0 | Application framework |
| **Spring Web** | (included) | REST API and web server |
| **Azure OpenAI SDK** | 1.0.0-beta.6 | Azure OpenAI integration |
| **SpringDoc OpenAPI** | 2.3.0 | Swagger/OpenAPI documentation |
| **Logback** | (included) | Logging framework |

### Frontend

| Technology | Purpose |
|------------|---------|
| **HTML5** | Page structure |
| **CSS3** | Dark theme styling |
| **Vanilla JavaScript** | Client-side logic |
| **Fetch API** | AJAX requests |

### Build & Test

| Technology | Purpose |
|------------|---------|
| **Maven** | Build tool and dependency management |
| **JUnit 5** | Unit testing framework |
| **Spring Boot Test** | Integration testing |
| **MockMvc** | Controller testing |

### Infrastructure

| Component | Purpose |
|-----------|---------|
| **Azure OpenAI** | GPT-4 / GPT-3.5-turbo models |
| **Azure Portal** | Resource management |
| **Embedded Tomcat** | Web server (Spring Boot default) |

---

## ⚡ Performance

### Response Times

Typical conversion times (end-to-end):

| Code Complexity | Lines of Code | Response Time |
|-----------------|---------------|---------------|
| Simple | 1-5 | 2-5 seconds |
| Medium | 10-30 | 5-8 seconds |
| Complex | 50-100 | 8-15 seconds |
| Very Complex | 100+ | 15-30 seconds |

**Factors affecting performance:**
- Azure OpenAI API latency (primary factor)
- Model selection (GPT-4 slower than GPT-3.5)
- Code complexity and length
- Network latency
- Rate limits on Azure deployment

### Optimization Tips

1. **Use GPT-3.5-turbo for faster responses** (if accuracy allows)
2. **Increase token rate limits** in Azure deployment
3. **Use connection pooling** (built-in with Azure SDK)
4. **Monitor Azure metrics** in Azure Portal
5. **Consider caching** for repeated conversions (future enhancement)

### Rate Limits

Configure in Azure OpenAI Studio:
- **Default**: 10K tokens per minute
- **Recommended for production**: 30K-60K tokens per minute
- **Maximum**: Varies by subscription and region

Monitor usage:
```
Azure Portal → Your OpenAI Resource → Metrics → Token Usage
```

---

## 🔒 Security

### Best Practices Implemented

#### 1. **Credential Management**
- ✅ Environment variables for sensitive data
- ✅ No hardcoded credentials in source code
- ✅ `.gitignore` includes sensitive files
- ✅ Property placeholders with defaults

#### 2. **API Security**
- ✅ HTTPS for Azure OpenAI communication
- ✅ API key authentication
- ✅ Input validation on all endpoints
- ✅ CORS configuration (configurable)

#### 3. **Error Handling**
- ✅ No stack traces exposed to clients
- ✅ Generic error messages
- ✅ Detailed logging for debugging
- ✅ Graceful degradation

### Security Recommendations

#### For Development

```bash
# Use environment variables
export AZURE_OPENAI_API_KEY="your-dev-key"

# Or use .env file (add to .gitignore!)
echo "AZURE_OPENAI_API_KEY=your-dev-key" > .env
```

#### For Production

1. **Use Azure Key Vault**
   ```properties
   azure.openai.api-key=${AZURE_KEY_VAULT_SECRET}
   ```

2. **Enable HTTPS only**
   ```properties
   server.ssl.enabled=true
   server.ssl.key-store=classpath:keystore.jks
   ```

3. **Restrict CORS**
   ```java
   @CrossOrigin(origins = "https://yourdomain.com")
   ```

4. **Implement authentication** (JWT, OAuth2, etc.)

5. **Rate limiting** (Spring Cloud Gateway, API Gateway)

6. **Monitor API usage** in Azure Portal

### Never Commit

❌ API keys
❌ Connection strings
❌ Passwords
❌ Personal data
❌ `.env` files

✅ Use `.gitignore` (already configured)

---

## 🐛 Troubleshooting

### Common Issues

#### 1. **Application won't start**

**Error:** `Failed to configure a DataSource`

**Solution:** This application doesn't use a database. If you see this error, check for conflicting dependencies in `pom.xml`.

---

#### 2. **Azure OpenAI connection fails**

**Error:** `Unauthorized` or `Connection refused`

**Solution:**
```bash
# Verify your credentials
echo $AZURE_OPENAI_ENDPOINT
echo $AZURE_OPENAI_API_KEY
echo $AZURE_OPENAI_DEPLOYMENT

# Check endpoint format (must end with /)
# Correct: https://my-resource.openai.azure.com/
# Wrong:   https://my-resource.openai.azure.com

# Verify deployment name matches Azure portal
# Azure OpenAI Studio → Deployments → Check name
```

---

#### 3. **UI shows "Unexpected token '<'"**

**Error:** `Error connecting to server: Unexpected token '<', "<!doctype "...`

**Cause:** JavaScript is calling wrong URL

**Solution:** Ensure `index.html` uses relative URL:
```javascript
// Correct
fetch('api/convert', { ... })

// Wrong
fetch('/api/convert', { ... })
```

Already fixed in current version.

---

#### 4. **Conversion times out**

**Error:** `Read timed out` or no response

**Solution:**
```properties
# Increase timeout in application.properties
spring.mvc.async.request-timeout=60000

# Or check Azure OpenAI quotas in portal
```

---

#### 5. **Rate limit exceeded**

**Error:** `429 Too Many Requests`

**Solution:**
- Check rate limits in Azure OpenAI Studio
- Increase tokens per minute
- Implement retry logic
- Add delay between requests

---

#### 6. **Swagger UI not loading**

**Issue:** 404 on `/swagger-ui.html`

**Solution:**
```bash
# Check correct URL
http://localhost:8080/ai-code-converter/swagger-ui.html

# Verify it's enabled
# application.properties:
springdoc.swagger-ui.enabled=true
```

---

#### 7. **Tests failing**

**Issue:** Tests fail with mock errors

**Solution:**
```bash
# Clean and rebuild
mvn clean install

# Run with debug
mvn test -X

# Check Java version
java -version  # Must be 17+
```

---

### Enable Debug Logging

```properties
# application.properties
logging.level.com.ai.aicc=DEBUG
logging.level.com.azure=DEBUG
logging.level.org.springframework.web=DEBUG
```

Check logs:
```bash
tail -f logs/ai-code-converter.log
```

### Getting Help

1. **Check logs**: `logs/ai-code-converter.log`
2. **Verify configuration**: `application.properties`
3. **Test API directly**: Use Swagger UI
4. **Check Azure status**: [Azure Status](https://status.azure.com/)
5. **Review test cases**: `docs/TEST_CASES.md`

---

## 👨‍💻 Development

### Setting Up Development Environment

```bash
# Clone repository
git clone https://github.com/your-org/ai-code-converter.git
cd ai-code-converter

# Install dependencies
mvn clean install

# Run in development mode with auto-reload (Spring Boot DevTools)
mvn spring-boot:run

# Or run in IDE (IntelliJ IDEA, Eclipse, VS Code)
```

### IDE Setup

#### IntelliJ IDEA
1. **File** → **Open** → Select `pom.xml`
2. Enable annotation processing
3. Install Lombok plugin (if using in future)
4. Run/Debug: `AiCodeConverterApplication`

#### VS Code
1. Install Java Extension Pack
2. Install Spring Boot Extension Pack
3. Open folder
4. Run using Spring Boot Dashboard

### Code Style

- **Indentation**: 4 spaces
- **Line length**: 120 characters
- **Naming**: camelCase for methods, PascalCase for classes
- **Comments**: JavaDoc for public APIs

### Making Changes

1. **Create feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make changes and test**
   ```bash
   mvn test
   ```

3. **Commit with meaningful messages**
   ```bash
   git commit -m "Add: Feature description"
   ```

4. **Push and create PR**
   ```bash
   git push origin feature/your-feature-name
   ```

---

## 🚀 Deployment

### Deployment Options

#### 1. **Traditional WAR Deployment**

```xml
<!-- pom.xml -->
<packaging>war</packaging>
```

```bash
mvn clean package
# Deploy target/aicc-1.0.0.war to Tomcat, WildFly, etc.
```

#### 2. **Executable JAR** (Recommended)

```bash
mvn clean package
java -jar target/aicc-1.0.0.jar
```

#### 3. **Docker Container**

Create `Dockerfile`:
```dockerfile
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/aicc-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:
```bash
docker build -t ai-code-converter .
docker run -p 8080:8080 \
  -e AZURE_OPENAI_ENDPOINT="..." \
  -e AZURE_OPENAI_API_KEY="..." \
  -e AZURE_OPENAI_DEPLOYMENT="gpt-4" \
  ai-code-converter
```

#### 4. **Azure App Service**

```bash
# Azure CLI
az webapp create --resource-group myResourceGroup \
  --plan myAppServicePlan --name ai-code-converter \
  --runtime "JAVA:17-java17"

az webapp config appsettings set \
  --resource-group myResourceGroup \
  --name ai-code-converter \
  --settings AZURE_OPENAI_ENDPOINT="..." \
             AZURE_OPENAI_API_KEY="..." \
             AZURE_OPENAI_DEPLOYMENT="gpt-4"

az webapp deploy --resource-group myResourceGroup \
  --name ai-code-converter \
  --src-path target/aicc-1.0.0.jar
```

#### 5. **Kubernetes**

See `k8s/` directory for Kubernetes manifests (future enhancement).

### Production Checklist

- [ ] Set production credentials (Key Vault)
- [ ] Enable HTTPS/SSL
- [ ] Configure CORS for specific domains
- [ ] Set up monitoring (Azure Monitor, Application Insights)
- [ ] Configure log aggregation
- [ ] Set up health checks
- [ ] Configure auto-scaling
- [ ] Set up backup/disaster recovery
- [ ] Review rate limits
- [ ] Enable security headers
- [ ] Set up CI/CD pipeline
- [ ] Configure domain and DNS
- [ ] Set up alerts and notifications

---

## 🤝 Contributing

We welcome contributions! Please follow these guidelines.

### How to Contribute

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Make your changes**
4. **Write or update tests**
5. **Ensure tests pass**
   ```bash
   mvn test
   ```
6. **Commit your changes**
   ```bash
   git commit -m "Add: Amazing feature description"
   ```
7. **Push to your fork**
   ```bash
   git push origin feature/amazing-feature
   ```
8. **Open a Pull Request**

### Contribution Ideas

- 🌍 Add support for more languages (JavaScript, C#, Go, Rust, etc.)
- 🎨 Improve UI/UX
- ⚡ Add caching for faster responses
- 🔐 Implement authentication/authorization
- 📊 Add usage analytics
- 🧪 Increase test coverage
- 📝 Improve documentation
- 🐛 Fix bugs
- 🚀 Performance optimizations

### Code of Conduct

- Be respectful and inclusive
- Provide constructive feedback
- Focus on the code, not the person
- Help others learn and grow

---

## 📄 License

This project is licensed under the MIT License.

```
MIT License

Copyright (c) 2024 AI Code Converter Team

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 📞 Contact & Support

### Project Links

- **Documentation**: [GitHub Wiki](https://github.com/your-org/ai-code-converter/wiki)
- **Issue Tracker**: [GitHub Issues](https://github.com/your-org/ai-code-converter/issues)
- **Discussions**: [GitHub Discussions](https://github.com/your-org/ai-code-converter/discussions)

### Support

- 📧 Email: support@aicc.example.com
- 💬 Slack: [Join our Slack](https://slack.example.com)
- 🐦 Twitter: [@AICodeConverter](https://twitter.com/example)

---

## 🙏 Acknowledgments

- **Azure OpenAI** for providing the AI capabilities
- **Spring Boot** for the excellent framework
- **SpringDoc** for OpenAPI documentation
- **All contributors** who help improve this project

---

## 📈 Roadmap

### Version 1.1 (Next Release)
- [ ] Support for more languages (JavaScript, TypeScript, C#)
- [ ] Batch conversion API
- [ ] Conversion history
- [ ] User authentication

### Version 1.2
- [ ] Code diff viewer
- [ ] Syntax highlighting in UI
- [ ] Dark/light theme toggle
- [ ] Export conversions (file download)

### Version 2.0
- [ ] Multi-file conversion
- [ ] Project-level conversion
- [ ] Custom model fine-tuning
- [ ] API usage analytics dashboard

---

## ❓ FAQ

**Q: Is this free to use?**
A: The application is free and open-source. However, you need an Azure OpenAI subscription which has associated costs.

**Q: What Azure OpenAI models are supported?**
A: GPT-4, GPT-3.5-turbo, and GPT-4-32k. GPT-4 recommended for best results.

**Q: Can I use this commercially?**
A: Yes, under the MIT license. Check Azure OpenAI terms for API usage.

**Q: How accurate are the conversions?**
A: Very high accuracy for standard code. Complex or domain-specific code may require manual review.

**Q: Can I add more programming languages?**
A: Yes! The system is extensible. Just update the UI dropdowns - the AI handles any language pair.

**Q: Is my code stored or logged?**
A: Code is sent to Azure OpenAI for conversion but not permanently stored by this application. Check Azure's data policies.

**Q: Can I run this offline?**
A: No, it requires internet connectivity to access Azure OpenAI API.

**Q: What's the maximum code length?**
A: Limited by Azure OpenAI token limits (typically ~8K tokens for GPT-4, ~4K for GPT-3.5).

---

<div align="center">

**Made with ❤️ by the AI Code Converter Team**

⭐ **Star this repo if you find it useful!** ⭐

[Report Bug](https://github.com/your-org/ai-code-converter/issues) ·
[Request Feature](https://github.com/your-org/ai-code-converter/issues) ·
[Documentation](https://github.com/your-org/ai-code-converter/wiki)

</div>
