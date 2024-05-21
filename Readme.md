# <span style="color: #47a699;">Accounting Application</span>

The `Accounting Application` is a Maven-based Spring Boot application, leveraging version `3.2.5` of Spring Boot and Java `17`. It's designed to provide accounting functionalities and integrates with tools and platforms like PostgreSQL, Docker, and AWS.


# <span style="color: #47a699;">Prerequisites</span>

Before you begin, ensure you have met the following requirements:

- Java 17
- Maven
- Docker
- Access to PostgreSQL (locally or through a service)



# <span style="color: #47a699;">Setup & Running the Service</span>

Depending on your operating system, follow the appropriate steps to get the service up and running:


<span style="color: #ada95c; font-weight: bold">Postgres:</span>
``docker run -d --name pgdb -v ./pgdata:/var/lib/mysql/data/pgdata -e POSTGRES_USERNAME=postgres -e POSTGRES_PASSWORD=admin -p 5432:5432 postgres``


<span style="color: #ada95c; font-weight: bold">Keycloak:</span>
``docker run -d --name keycloak_22 -v ./keycloak:/opt/keycloak/data/h2 -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_A``

<span style="color: #ada95c; font-weight: bold">Swagger specification</span>
  can be accessed using this url ``http://localhost:8081/api/v1/swagger-ui/index.html``

---


# <span style="color: #47a699;">Java and Spring Boot Coding Standards</span>


## <span style="color: #476ba6;">1. Code Formatting</span>

- **Indentation:**
    - Use 4 spaces for indentation, not tabs.
- **Braces:**
    - Always use braces, even for single statement blocks.
- **Line Length:**
    - Keep lines to a maximum of 120 characters.
- **Whitespace:**
    - Avoid trailing whitespaces.


## <span style="color: #476ba6;">2. Naming Conventions</span>

- **Classes:**
    - Start with an uppercase and use CamelCase.
    - *Example:* `UserService`

- **Methods:**
    - Start with a lowercase and use camelCase.
    - *Example:* `getUserData()`
- **Variables:**
    - Use meaningful names and avoid single-letter names (except for loop indexes).
    - *Example:* `userList`, `accountStatus`
- **Constants:**
    - Use uppercase with underscores.
    - *Example:* `MAX_RETRY_COUNT`

## <span style="color: #476ba6;">3. Comments</span>

- Write meaningful comments and avoid obvious comments.
- Use Javadoc style comments for classes and methods.
- Comment any code that might appear non-trivial or has business implications.


## <span style="color: #476ba6;">4. Spring Boot Specifics</span>

- **Annotations:**
    - Place Spring Boot's annotations in this order: `@SpringBootApplication`, `@RestController`, `@RequestMapping`, etc.
- **Property Injection:**
    - Use constructor injection over field injection for better testability.
- **Exception Handling:**
    - Use `@ControllerAdvice` and `@ExceptionHandler` to handle exceptions globally.

## <span style="color: #476ba6;">5. General Guidelines</span>

- **Single Responsibility Principle:**
    - A class should have only one reason to change.
- **Avoid Magic Numbers:**
    - Instead of hardcoding numbers, use named constants.
- **Null Safety:**
    - Always check for `null` before using an object or provide default values using `Optional`.
- **Logging:**
    - Use appropriate logging levels (INFO, DEBUG, ERROR) and always log exceptions.
- **Unit Testing:**
    - Always write unit tests for your service layers. Aim for a high code coverage.

## <span style="color: #476ba6;">6. Dependencies</span>

- Keep your dependencies up to date.
- Avoid using deprecated libraries or methods.
- Use Maven or Gradle's `scope` to ensure runtime-only libraries don't get bundled at compile-time.

## <span style="color: #476ba6;">7. Database</span>

- **Naming:**
    - Use snake_case for table and column names.
- **Indexes:**
    - Always index foreign keys and frequently searched fields.
- **Lazy Loading:**
    - Be cautious when using lazy loading with Hibernate to avoid N+1 problems.

---
