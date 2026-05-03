# SafeTrip

SafeTrip is basically a deployed travel web platform for tourists and also for discovering tours, food places, entertainment spots and safety information in local city.

## 1. Project Title

**SafeTrip**

## 2. One-Line Description

A web application for exploring Almaty. It helps users visit local city, save favorite places, book tours, and recover accounts through Telegram OTP.

## 3. Topic Area

TravelTech / City Guide Platform / Tourism Web Application

## 4. Problem Statement

Tourists and first-time visitors usually have to use several different sources to find tours, restaurants, entertainments and safety information about a city.

This results in a fragmented experience where users can not easily browse, save, book and manage their travel plans from one platform. 

Another issue is account recovery. Email-based password reset can be unreliable because free email providers and regional restrictions may block delivery.

SafeTrip addresses these problems by offering a centralized travel platform with secure authentication and Telegram-based password recovery.

## 5. Proposed Solution

SafeTrip Almaty provides one platform where users can:

- Browse tours, food places, and entertainment spots
- View detailed information about each tour or place
- Save favorite tours and places
- Book tours and receive ticket information
- Manage saved items and bookings in the profile page
- Recover passwords using Telegram OTP instead of email
- Allow administrators to manage platform content

The password reset flow is a Telegram bot. To register, the user links their Telegram account by sending a token created to the bot. If at a later time the user cannot remember the password, the system issues a one-time reset code to the linked Telegram account.

## 6. Target Users

- Tourists visiting Almaty
- Local users looking for tours, food, and entertainment
- Students and young travelers
- Platform administrators
- Small tourism-related businesses

## 7. Key Features

### User Features

- User registration
- User login
- JWT-based authentication
- Telegram account connection
- Telegram OTP password reset
- Browse tours
- Browse food places
- Browse entertainment places
- View tour details
- View place details
- Add tours and places to favorites
- Remove tours and places from favorites
- Book tours
- View tour tickets in the profile page
- Cancel bookings from the profile page

### Admin Features

- Admin login
- Create, update, and delete tours
- Create, update, and delete food and entertainment places
- Manage platform content through the admin panel

### Security Features

- Password hashing
- JWT authentication
- Role-based authorization
- Protected admin routes
- Internal Telegram bot API protected by shared secret
- Password reset OTP expiration
- Password reset attempt limits
- Password reset token expiration

## 8. Technology Stack

### Frontend

- React
- Vite
- JavaScript
- React Router
- Axios
- CSS

### Backend

- Java 21
- Spring Boot
- Spring Web
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- Maven

### Database

- PostgreSQL
- Flyway migrations

### Telegram Bot Service

- Python
- FastAPI
- Uvicorn
- python-telegram-bot
- HTTPX
- Telegram Bot API

### Cloud / Hosting

- Render
- Render PostgreSQL
- Docker
- Docker Compose

### Other Tools

- Git
- GitHub
- pgAdmin
- cron-job.org for Render wake-up pings

## 9. System Architecture

```text
User Browser
    |
    v
React Frontend
    |
    v
Spring Boot Backend
    |
    +--> PostgreSQL Database
    |
    +--> Telegram Bot Service
              |
              v
          Telegram Bot API
```

## 10. Project Structure

```text
tourGit/
├── safetrip-front/
│   ├── src/
│   │   ├── api/
│   │   ├── components/
│   │   ├── pages/
│   │   └── utils/
│   └── package.json
│
├── src/main/java/kz/safetrip/safetrip/
│   ├── config/
│   ├── controller/
│   ├── model/
│   ├── repository/
│   └── service/
│
├── src/main/resources/
│   ├── application.properties
│   └── db/migration/
│
├── telegram-bot/
│   ├── main.py
│   ├── Dockerfile
│   ├── requirements.txt
│   └── README.md
│
├── docker-compose.yml
├── Dockerfile
├── pom.xml
├── README.md
├── AUDIT.md
├── LICENSE
└── .gitignore
```

## 11. Installation and Local Setup

### Prerequisites

Install the following tools before running the project locally:

```text
Java 21
Maven
Node.js
Docker
Docker Compose
Git
```

### Clone the Repository

```bash
git clone https://github.com/erslvv/tourGit.git
cd tourGit
```

### Environment Variables

Create a `.env` file in the project root.

Example:

```env
TELEGRAM_BOT_USERNAME=safetrip_kz_bot
TELEGRAM_BOT_TOKEN=your_telegram_bot_token
TELEGRAM_BOT_SECRET=your_shared_secret

TELEGRAM_BOT_SERVICE_URL=http://telegram-bot:8001
JAVA_API_BASE_URL=http://app:8080

TELEGRAM_BIND_EXPIRATION_MINUTES=15
PASSWORD_RESET_EXPIRATION_MINUTES=15
PASSWORD_RESET_OTP_EXPIRATION_MINUTES=5
PASSWORD_RESET_OTP_MAX_ATTEMPTS=5
```

Do not commit `.env` to GitHub.

### Run with Docker Compose

Run the full project locally:

```bash
docker compose --profile telegram up -d --build
```

Open the application:

```text
Frontend: http://localhost:3000
Backend: http://localhost:8080
Telegram bot health: http://localhost:8001/health
pgAdmin: http://localhost:5050
```

### Run Frontend Separately

```bash
cd safetrip-front
npm install
npm run dev
```

### Run Backend Separately

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```bash
./mvnw.cmd spring-boot:run
```

### Run Telegram Bot Separately

```bash
cd telegram-bot
pip install -r requirements.txt
python main.py
```

## 12. Usage Instructions

### Registration and Telegram Connection

1. Open the frontend application.
2. Create a new user account.
3. The system generates a Telegram connection token.
4. Open the SafeTrip Telegram bot.
5. Send the generated `bind_...` token to the bot.
6. The bot connects the Telegram account to the SafeTrip user account.
7. After successful connection, the user can use Telegram password reset.

### Password Reset

1. Open the Forgot Password page.
2. Enter the email of an account that has Telegram connected.
3. The backend generates a 6-digit OTP.
4. The Telegram bot sends the OTP to the connected Telegram account.
5. The user enters the OTP on the website.
6. The backend verifies the OTP and returns a reset token.
7. The user sets a new password.

### Tour Booking

1. Open a tour detail page.
2. Book the tour.
3. The system generates a ticket.
4. The ticket appears in the user profile.
5. The user can view or cancel the booking.

### Admin Usage

1. Log in with an admin account.
2. Open the admin panel.
3. Create, edit, or delete tours and places.

## 13. Deployment

The project is deployed using Render.

### Backend Render Environment Variables

```env
APP_CORS_ALLOWED_ORIGIN_PATTERNS=https://*.onrender.com
APP_SECURITY_JWT_EXPIRATION_MINUTES=1440
APP_SECURITY_JWT_SECRET=your_jwt_secret

SPRING_DATASOURCE_URL=your_postgresql_jdbc_url
SPRING_DATASOURCE_USERNAME=your_database_username
SPRING_DATASOURCE_PASSWORD=your_database_password
SPRING_JPA_HIBERNATE_DDL_AUTO=validate
SPRING_JPA_SHOW_SQL=false

TELEGRAM_BOT_USERNAME=safetrip_kz_bot
TELEGRAM_BOT_SECRET=your_shared_secret
TELEGRAM_BOT_SERVICE_URL=https://your-telegram-bot-service.onrender.com

TELEGRAM_BIND_EXPIRATION_MINUTES=15
PASSWORD_RESET_EXPIRATION_MINUTES=15
PASSWORD_RESET_OTP_EXPIRATION_MINUTES=5
PASSWORD_RESET_OTP_MAX_ATTEMPTS=5
```

### Telegram Bot Render Environment Variables

```env
BOT_TOKEN=your_telegram_bot_token
BOT_SHARED_SECRET=your_shared_secret
JAVA_API_BASE_URL=https://your-backend-service.onrender.com
PORT=10000
```

### Render Free Tier Note

The backend and Telegram bot are hosted on Render free services. Free services may sleep after inactivity. To reduce cold starts, health-check pings can be configured through cron-job.org.

Recommended ping URLs:

```text
https://your-backend-service.onrender.com
https://your-telegram-bot-service.onrender.com/health
```

## 14. Screenshots

Screenshots can be added in the repository under an `assets/` folder.

Example format:

```md
![Login Page](assets/login-page.png)
![Tours Page](assets/tours-page.png)
![Profile Page](assets/profile-page.png)
```

## 15. Known Issues

- Render free services may sleep after inactivity.
- Telegram polling requires only one active bot instance. Running the same bot token locally and on Render at the same time can cause Telegram `getUpdates` conflicts.
- Telegram bind tokens expire after a limited time.
- A Telegram account can be connected to only one SafeTrip user account.
- If a Telegram bind token is expired or already used, the user must generate a new token from the website.

## 16. Expected Outcome

The final outcome is a working deployed web application with:

- React frontend
- Spring Boot backend
- PostgreSQL database
- Telegram bot service
- User and admin functionality
- Tour and place browsing
- Favorites
- Tour booking
- Telegram-based password reset

## 17. Git Repository

```text
https://github.com/erslvv/tourGit
```

## 18. Demo Credentials

```text
Admin:
Email: admin@safetrip.kz
Password: admin123

User:
Email: demo@safetrip.kz
Password: demo123
```

If these accounts are removed from the deployed database, create a new account through the registration page.

## 19. Team Members and Student IDs

- 230103174
- 230103215
- 230103128
- 230103289

## 20. License

This project is created for educational purposes.
