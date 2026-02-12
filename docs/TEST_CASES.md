# AI Code Converter - Test Cases

This document contains test cases for validating the code conversion functionality across different programming languages.

---

## Test Case 1: SQL to Java - Simple SELECT Query

**Source Language:** SQL
**Target Language:** Java
**Description:** Convert a basic SELECT query to Java JDBC code

### Input (SQL):
```sql
SELECT * FROM users WHERE age > 18 AND status = 'active'
```

### Expected Output (Java):
Should generate Java code with PreparedStatement and proper parameter binding.

### API Test:
```bash
curl -X POST http://localhost:8080/ai-code-converter/api/convert \
  -H "Content-Type: application/json" \
  -d '{
    "sourceCode": "SELECT * FROM users WHERE age > 18 AND status = '\''active'\''",
    "sourceLanguage": "SQL",
    "targetLanguage": "Java"
  }'
```

---

## Test Case 2: SQL to Java - INSERT Statement

**Source Language:** SQL
**Target Language:** Java
**Description:** Convert INSERT statement to Java code

### Input (SQL):
```sql
INSERT INTO employees (name, email, department, salary)
VALUES ('John Doe', 'john@example.com', 'Engineering', 75000)
```

### Expected Output (Java):
Should generate PreparedStatement with proper parameter insertion.

---

## Test Case 3: Java to SQL - Simple Method

**Source Language:** Java
**Target Language:** SQL
**Description:** Convert Java method that queries data to SQL

### Input (Java):
```java
public List<User> getActiveUsers() {
    List<User> users = new ArrayList<>();
    String query = "SELECT id, name, email FROM users WHERE status = 'active'";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            users.add(user);
        }
    }
    return users;
}
```

### Expected Output (SQL):
Should extract and optimize the SQL query.

---

## Test Case 4: Java to Python - Class Conversion

**Source Language:** Java
**Target Language:** Python
**Description:** Convert a Java class to Python

### Input (Java):
```java
public class Calculator {
    private int result;

    public Calculator() {
        this.result = 0;
    }

    public int add(int a, int b) {
        result = a + b;
        return result;
    }

    public int subtract(int a, int b) {
        result = a - b;
        return result;
    }

    public int getResult() {
        return result;
    }
}
```

### Expected Output (Python):
Should generate a Python class with proper syntax and conventions.

### API Test:
```bash
curl -X POST http://localhost:8080/ai-code-converter/api/convert \
  -H "Content-Type: application/json" \
  -d '{
    "sourceCode": "public class Calculator {\n    private int result;\n    \n    public Calculator() {\n        this.result = 0;\n    }\n    \n    public int add(int a, int b) {\n        result = a + b;\n        return result;\n    }\n    \n    public int subtract(int a, int b) {\n        result = a - b;\n        return result;\n    }\n    \n    public int getResult() {\n        return result;\n    }\n}",
    "sourceLanguage": "Java",
    "targetLanguage": "Python"
  }'
```

---

## Test Case 5: Python to Java - Function Conversion

**Source Language:** Python
**Target Language:** Java
**Description:** Convert Python functions to Java methods

### Input (Python):
```python
def calculate_average(numbers):
    if not numbers:
        return 0

    total = sum(numbers)
    count = len(numbers)
    average = total / count

    return average

def find_max(numbers):
    if not numbers:
        return None

    max_value = numbers[0]
    for num in numbers:
        if num > max_value:
            max_value = num

    return max_value
```

### Expected Output (Java):
Should generate Java methods with proper types and null handling.

---

## Test Case 6: Python to Java - List Operations

**Source Language:** Python
**Target Language:** Java
**Description:** Convert Python list operations to Java

### Input (Python):
```python
def filter_even_numbers(numbers):
    return [num for num in numbers if num % 2 == 0]

def square_numbers(numbers):
    return [num ** 2 for num in numbers]

def sum_of_squares(numbers):
    squared = [num ** 2 for num in numbers]
    return sum(squared)
```

### Expected Output (Java):
Should generate Java code using streams or traditional loops.

---

## Test Case 7: SQL to Python - Complex Query

**Source Language:** SQL
**Target Language:** Python
**Description:** Convert SQL query to Python (using SQLAlchemy or similar)

### Input (SQL):
```sql
SELECT
    e.name,
    e.email,
    d.department_name,
    e.salary
FROM employees e
INNER JOIN departments d ON e.department_id = d.id
WHERE e.salary > 50000
ORDER BY e.salary DESC
LIMIT 10
```

### Expected Output (Python):
Should generate Python code with database query logic.

---

## Test Case 8: Python to SQL - Data Processing

**Source Language:** Python
**Target Language:** SQL
**Description:** Convert Python data filtering to SQL

### Input (Python):
```python
def get_high_salary_employees(employees):
    return [emp for emp in employees
            if emp['salary'] > 75000 and emp['department'] == 'Engineering']
```

### Expected Output (SQL):
Should generate equivalent SQL WHERE clause.

---

## Test Case 9: Java to Python - Spring Boot Controller

**Source Language:** Java
**Target Language:** Python
**Description:** Convert Spring Boot REST controller to Python Flask

### Input (Java):
```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
```

### Expected Output (Python):
Should generate Flask or FastAPI equivalent.

---

## Test Case 10: Error Handling - Empty Input

**Source Language:** Any
**Target Language:** Any
**Description:** Test validation for empty source code

### Input:
```json
{
  "sourceCode": "",
  "sourceLanguage": "Java",
  "targetLanguage": "Python"
}
```

### Expected Output:
```json
{
  "convertedCode": null,
  "sourceLanguage": null,
  "targetLanguage": null,
  "success": false,
  "error": "Source code cannot be empty"
}
```

### API Test:
```bash
curl -X POST http://localhost:8080/ai-code-converter/api/convert \
  -H "Content-Type: application/json" \
  -d '{
    "sourceCode": "",
    "sourceLanguage": "Java",
    "targetLanguage": "Python"
  }'
```

---

## Test Case 11: Python to Java - Dictionary/Map Operations

**Source Language:** Python
**Target Language:** Java
**Description:** Convert Python dictionary operations to Java Map

### Input (Python):
```python
def count_word_frequency(text):
    words = text.lower().split()
    frequency = {}

    for word in words:
        if word in frequency:
            frequency[word] += 1
        else:
            frequency[word] = 1

    return frequency
```

### Expected Output (Java):
Should generate Java code using HashMap.

---

## Test Case 12: Java to Python - Exception Handling

**Source Language:** Java
**Target Language:** Python
**Description:** Convert Java try-catch to Python try-except

### Input (Java):
```java
public void readFile(String filename) {
    try {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    } catch (FileNotFoundException e) {
        System.err.println("File not found: " + filename);
    } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
    }
}
```

### Expected Output (Python):
Should generate Python code with try-except blocks.

---

## Quick Test Commands

### Test all conversions quickly:

**SQL → Java:**
```bash
curl -X POST http://localhost:8080/ai-code-converter/api/convert \
  -H "Content-Type: application/json" \
  -d '{"sourceCode": "SELECT * FROM users WHERE id = 1", "sourceLanguage": "SQL", "targetLanguage": "Java"}'
```

**Java → Python:**
```bash
curl -X POST http://localhost:8080/ai-code-converter/api/convert \
  -H "Content-Type: application/json" \
  -d '{"sourceCode": "public int add(int a, int b) { return a + b; }", "sourceLanguage": "Java", "targetLanguage": "Python"}'
```

**Python → Java:**
```bash
curl -X POST http://localhost:8080/ai-code-converter/api/convert \
  -H "Content-Type: application/json" \
  -d '{"sourceCode": "def add(a, b):\n    return a + b", "sourceLanguage": "Python", "targetLanguage": "Java"}'
```

**Python → SQL:**
```bash
curl -X POST http://localhost:8080/ai-code-converter/api/convert \
  -H "Content-Type: application/json" \
  -d '{"sourceCode": "users = [u for u in all_users if u.age > 18]", "sourceLanguage": "Python", "targetLanguage": "SQL"}'
```

---

## Testing Checklist

- [ ] Test Case 1: SQL to Java - Simple SELECT
- [ ] Test Case 2: SQL to Java - INSERT Statement
- [ ] Test Case 3: Java to SQL - Extract Query
- [ ] Test Case 4: Java to Python - Class Conversion
- [ ] Test Case 5: Python to Java - Function Conversion
- [ ] Test Case 6: Python to Java - List Operations
- [ ] Test Case 7: SQL to Python - Complex Query
- [ ] Test Case 8: Python to SQL - Data Filtering
- [ ] Test Case 9: Java to Python - REST Controller
- [ ] Test Case 10: Error Handling - Empty Input
- [ ] Test Case 11: Python to Java - Dictionary Operations
- [ ] Test Case 12: Java to Python - Exception Handling

---

## Expected Behavior

### Successful Conversion:
- `success: true`
- `convertedCode` contains valid, syntactically correct code
- Proper language conventions (camelCase for Java, snake_case for Python)
- Appropriate error handling in converted code
- Comments preserved where applicable

### Failed Conversion:
- `success: false`
- `error` field contains descriptive error message
- `convertedCode` is null

---

## Performance Benchmarks

**Expected Response Times:**
- Simple conversions (1-5 lines): 2-5 seconds
- Medium conversions (10-30 lines): 5-8 seconds
- Complex conversions (50+ lines): 8-15 seconds

Note: Response times depend on Azure OpenAI API latency and model deployment configuration.
