

# iCollege Backend API

This repository contains the backend service for the **iCollege** application. Built with Java and Spring Boot, this robust REST API handles the core business logic, user authentication, and data management for the iCollege platform.

---

##  Features

Based on the project's architecture and modules, this backend supports the following core functionalities:

* **Secure Authentication & User Management**:
* Implements role-based access control (`Role` enum) and JWT-based authentication (`JWTFilter`, `JWTService`).


* Handles user registration, login, profile management, and password resets (`UserAuthController`, `UserService`, `ResetPasswordRequest`).


* Utilizes OTP (One-Time Password) tokens for secure verifications (`OtpTokenRepository`).




* **Complaint Management System**:
* Users can submit and track issues across different categories (`ComplaintCategory`, `ComplaintStatus`).


* Supports interactive complaint threads where users can leave comments (`ComplaintCommentController`, `ComplaintCommentService`).




* **Campus Announcements**:
* Dedicated endpoints for broadcasting and retrieving college-wide announcements (`AnnouncementController`, `AnnouncementService`).




* **Opinion Polls**:
* Allows the creation of polls and tracking of user votes to gather campus feedback (`OpinionPollController`, `PollVoteRepository`).




* **Email Notifications**:
* Integrated email service for sending alerts, OTPs, or notifications (`EmailService.java`).




* **API Documentation**:
* Auto-generated Swagger/OpenAPI documentation configured for easy API testing and client integration (`SwaggerConfiguration.java`).





---

##  Tech Stack

* **Language:** Java


* **Framework:** Spring Boot


* **Build Tool:** Gradle (Kotlin DSL - `build.gradle.kts`)


* **Security:** Spring Security & JSON Web Tokens (JWT)


* **Containerization:** Docker (`Dockerfile`)


* **Documentation:** Swagger / OpenAPI



---

## 📂 Project Structure

The project follows a standard Spring Boot layered architecture:

* **`auth/`**: Contains security configurations, custom user details, and JWT token management classes.


* **`controllers/`**: Exposes REST endpoints for announcements, complaints, polls, and authentication.


* **`dtos/`**: Data Transfer Objects structured for specific requests and responses (e.g., `LoginRequest`, `ProfileResponse`) to ensure safe data binding.


* **`entities/`**: JPA models representing database tables (`UserInfo`, `Complaint`, `OpinionPoll`, etc.).


* **`repos/`**: Spring Data JPA interfaces for seamless database interactions.


* **`services/`**: The core business logic layer bridging controllers and repositories.



---

## ⚙️ Getting Started

### Prerequisites

* Java Development Kit (JDK) installed.
* Docker (optional, but recommended for containerized deployment).



### Running Locally

1. **Navigate to the project directory:**
Open your terminal and navigate to the root of the project where `build.gradle.kts` and the Gradle wrapper are located.


2. **Configure Environment:**
Update the `src/main/resources/application.properties` file with your local database credentials, JWT secrets, and email server configurations.


3. **Build and Run using Gradle Wrapper:**
```bash
# For Unix/macOS
./gradlew bootRun

# For Windows
gradlew.bat bootRun

```



### Running with Docker

Because this project includes a `Dockerfile`, you can easily build and run it in an isolated container:

1. **Build the Docker image:**
```bash
docker build -t icollege-backend .

```


2. **Run the container:**
```bash
docker run -p 8080:8080 icollege-backend

```
