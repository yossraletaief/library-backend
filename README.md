# Spring Boot Backend - Online Library Management System
This is the backend part of the Online Library Management System, built using **Spring Boot**. It provides RESTful APIs for managing books and integrates with an external AI service (e.g., OpenAI) to generate insights such as summaries or taglines for books.

## Prerequisites

Before starting, ensure you have the following installed:

- **Java 11 or higher**
- **Maven** for building the project
- **An API key from OpenAI**  for generating insights

## Setting up the Spring Boot Backend

### 1. Clone the repository

```bash
git clone [<repository-url>](https://github.com/yossraletaief/library-backend)
cd library-backend
```

### 2. Configure AI API integration

To generate insights like summaries or taglines, configure the backend to connect to an AI service. For example, if you're using OpenAI, follow these steps:

- **Sign up for an API key** from OpenAI.
- **Add the API key** to your `application.properties` and `BookServiceImpl.java` file as follows:
  
  ```properties
  spring.ai.openai.api-key=your-api-key-here
  AI_SERVICE_URL = "https://api.openai.com/v1/chat/completions"
  AI_API_KEY=your-api-key-here
  ```

### 3. Build the application

Install dependencies and build the project:

```bash
mvn clean install
```

### 4. Run the Spring Boot application

Start the application with:

```bash
mvn spring-boot:run
```

The backend API will be available at `http://localhost:8080`.

### 5. API Endpoints
You can view the API documentation using Swagger UI at the following URL:
```bash
http://localhost:8080/swagger-ui/index.html
```
