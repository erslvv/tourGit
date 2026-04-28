# SafeTrip Render Deploy

This project is prepared for the following Render setup:

```text
Render PostgreSQL  -> database
Render Web Service -> Spring Boot backend
Render Static Site -> React/Vite frontend
```

## 1. Render PostgreSQL

Create PostgreSQL on Render:

```text
Name: safetrip-db
Database: safetrip
User: safetrip
Plan: Free
```

Use the Internal Host for backend connection. Spring Boot needs JDBC format:

```properties
SPRING_DATASOURCE_URL=jdbc:postgresql://INTERNAL_HOST:5432/safetrip
SPRING_DATASOURCE_USERNAME=safetrip
SPRING_DATASOURCE_PASSWORD=PASSWORD_FROM_RENDER
```

## 2. Backend Web Service

Create Render Web Service from the repository.

```text
Runtime: Docker
Root Directory: .
Plan: Free
```

Environment variables:

```properties
SPRING_DATASOURCE_URL=jdbc:postgresql://INTERNAL_HOST:5432/safetrip
SPRING_DATASOURCE_USERNAME=safetrip
SPRING_DATASOURCE_PASSWORD=PASSWORD_FROM_RENDER
SPRING_JPA_HIBERNATE_DDL_AUTO=validate
SPRING_JPA_SHOW_SQL=false
SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=false
APP_SECURITY_JWT_SECRET=CHANGE_THIS_TO_LONG_RANDOM_SECRET_32_PLUS_CHARS
APP_SECURITY_JWT_EXPIRATION_MINUTES=1440
APP_CORS_ALLOWED_ORIGIN_PATTERNS=https://*.onrender.com
```

After frontend is deployed, replace `APP_CORS_ALLOWED_ORIGIN_PATTERNS` with the exact frontend URL:

```properties
APP_CORS_ALLOWED_ORIGIN_PATTERNS=https://YOUR_FRONTEND.onrender.com
```

Backend check URL:

```text
https://YOUR_BACKEND.onrender.com/swagger-ui.html
```

## 3. Frontend Static Site

Create Render Static Site from the same repository.

```text
Root Directory: safetrip-front
Build Command: npm ci && npm run build
Publish Directory: dist
Plan: Free
```

Environment variable:

```properties
VITE_API_URL=https://YOUR_BACKEND.onrender.com
```

Do not add `/api` at the end of `VITE_API_URL`.

## 4. React Router rewrite

In Render Static Site, add Rewrite rule:

```text
Source: /*
Destination: /index.html
Action: Rewrite
```

## 5. Demo accounts

Seed data creates these users:

```text
demo@safetrip.kz
admin@safetrip.kz
```

The password is whatever matches the BCrypt hash in `V2__seed_data.sql`. If you do not know it, reset the admin password directly in DB or register a normal user for non-admin flows.

## 6. Security/deploy changes included

- New registrations are always created as `USER`.
- `POST/PUT/DELETE` for tours and places are restricted to `ADMIN`.
- User/audit/popularity admin endpoints are restricted to `ADMIN`.
- CORS is configurable by environment variable.
- Backend binds to Render `$PORT`.
- Backend Dockerfile uses `mvn` directly instead of Maven Wrapper.
