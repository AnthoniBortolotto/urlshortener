# URL Shortener

A URL shortener service built with Spring Boot and MongoDB, created as a learning project to explore Java development.

## Learning Objectives

This project serves as a practical way to learn and practice:
- Java programming fundamentals
- Spring Boot framework
- MongoDB database integration
- RESTful API design
- Unit testing with JUnit and Mockito
- Docker containerization
- Best practices in software development

## Features

- Shorten long URLs to manageable lengths
- Redirect short URLs to original destinations
- Track URL access statistics
- Prevent duplicate short URLs for the same original URL
- Docker containerization for easy deployment
- Comprehensive test coverage

## Technical Stack

- Java 17
- Spring Boot 3.5.0
- MongoDB
- Docker & Docker Compose
- JUnit 5 & Mockito
- Maven

## Project Structure

```
src/main/java/com/urlshortener/urlshortener/
├── dto/              # Data Transfer Objects
├── entities/         # MongoDB entities
├── exceptions/       # Custom exceptions
├── repositories/     # MongoDB repositories
└── v1/              # API version 1
    ├── controllers/  # REST controllers
    └── services/    # Business logic
```

## Getting Started

1. Clone the repository
2. Build the project:
   ```bash
   ./mvnw clean package
   ```
3. Run with Docker:
   ```bash
   docker-compose up --build
   ```

## API Endpoints

- `POST /api/v1/shorten` - Create a short URL
- `GET /api/v1/redirect/{shortUrl}` - Redirect to original URL

## Testing

Run tests with coverage:
```bash
./mvnw test
```

View coverage report at: `target/site/jacoco/index.html`

## Learning Journey

This project is part of my journey to learn Java development. Key learning points include:

1. **Spring Boot Basics**
   - Dependency injection
   - REST controllers
   - Service layer design
   - Repository pattern

2. **MongoDB Integration**
   - Document modeling
   - Indexing
   - Atomic operations
   - Data persistence

3. **Testing Practices**
   - Unit testing
   - Mocking
   - Test coverage
   - Best practices

4. **Docker & Containerization**
   - Multi-container applications
   - Service networking
   - Environment configuration
   - Container orchestration

## Future Improvements

- Add user authentication
- Implement URL expiration
- Add rate limiting
- Create a web interface
- Add more comprehensive logging
- Implement caching

## Contributing

Feel free to contribute to this learning project! Any suggestions, improvements, or bug fixes are welcome.

## License

This project is open source and available under the MIT License. 