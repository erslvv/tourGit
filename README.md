# SafeTrip Backend

A Spring Boot backend application for the SafeTrip project with PostgreSQL, Flyway, JWT-based security, and Swagger/OpenAPI documentation.

## Problem Statement
Travel-related applications need a reliable backend to manage data, database migrations, API security, and service documentation. This project provides a backend foundation for such a system using Spring Boot and PostgreSQL.

## Features
- Spring Boot backend application
- PostgreSQL database integration
- Flyway database migrations
- JWT-based authentication configuration
- API documentation with Swagger / Springdoc
- Dockerfile for containerization
- Docker Compose support for local environment setup
- Maven wrapper for easier project execution

## Project Structure
```text
tourGit/
├── src/
│   └── main/
│       ├── java/
│       └── resources/
├── docs/
├── tests/
├── assets/
├── README.md
├── AUDIT.md
├── .gitignore
├── LICENSE
├── Dockerfile
├── docker-compose.yml
├── mvnw
├── mvnw.cmd
└── pom.xml