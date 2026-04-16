# SafeTrip

A travel-related application consisting of a Spring Boot backend and a separate frontend module.

## Problem Statement
Travel-related applications need a reliable backend for data management, API security, and database operations, as well as a frontend for user interaction. This project provides the foundation for a SafeTrip system with a backend built on Spring Boot and a separate frontend module for the user interface.

## Features
- Spring Boot backend application
- Separate frontend module (`safetrip-front`)
- PostgreSQL database integration
- Flyway database migrations
- JWT-based authentication configuration
- Swagger / Springdoc API documentation
- Dockerfile for containerization
- Docker Compose support for local environment setup
- Maven wrapper for simplified project startup

## Project Structure
```text
tourGit/
├── .mvn/
├── safetrip-front/
├── src/
│   └── main/
│       ├── java/
│       └── resources/
├── README.md
├── AUDIT.md
├── LICENSE
├── .gitignore
├── Dockerfile
├── docker-compose.yml
├── mvnw
├── mvnw.cmd
└── pom.xml