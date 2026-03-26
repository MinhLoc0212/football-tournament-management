# Football Tournament Management System

A comprehensive football tournament management system built with Spring Boot, supporting team registration, match scheduling, and professional financial statistics.

## 📋 Project Overview

This project is designed to simplify the organization and administration of football tournaments. The system provides powerful tools for both regular users (viewing information) and administrators (managing the tournament).

## ✨ Key Features

### Administrator (Admin)
- **Team Management:** Add, update, and manage the list of participating teams systematically.
- **Team Details:** Track detailed profiles, player rosters, and match history for each team.
- **Invoice Statistics:** Financial reporting system to track tournament revenue and expenses directly on the Admin dashboard.

### User
- View the list of teams and their detailed information.
- Follow match schedules and results (Under Development).

## 🛠️ Technologies Used

### Backend
- **Java 25**
- **Spring Boot 4.0.3**
- **Spring Data JPA** (Data Persistence)
- **Spring Validation** (Input Validation)
- **MySQL** (Database)

### Frontend
- **Thymeleaf** (Server-side Template Engine)
- **Bootstrap 5** (Responsive UI)
- **JavaScript & HTML5/CSS3**

### DevOps & Tools
- **Docker:** Containerization of the application.
- **GitHub Actions:** Automated CI/CD pipeline (Build & Push Docker Image).
- **Lombok:** Boilerplate code reduction.

## 🚀 Getting Started

1. **Prerequisites:**
   - Java 25 or higher.
   - Maven 3.9+.
   - MySQL Server.

2. **Database Configuration:**
   Update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/football_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Run Application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Docker Support:**
   ```bash
   docker build -t football-tournament-web .
   docker run -p 8080:8080 football-tournament-web
   ```

## 👨‍💻 My Role in Project
- Designed and developed the **Team Management** and **Team Details** modules.
- Built the **Invoice Statistics** system on the Admin dashboard.
- Configured the **CI/CD** workflow and **Dockerization** for the project.

## 🔗 Links
- **GitHub Repository:** [https://github.com/MinhLoc0212/football-tournament-management](https://github.com/MinhLoc0212/football-tournament-management)
