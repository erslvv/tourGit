Alpha deploy for teacher check

1. Open terminal in project root.
2. Run:
   docker compose up --build
3. Open frontend:
   http://localhost:3000
4. Backend Swagger:
   http://localhost:8080/swagger-ui.html
5. pgAdmin:
   http://localhost:5050
   login: admin@safetrip.com
   password: admin123

What starts together:
- frontend (React + Vite build served by Nginx)
- backend (Spring Boot)
- postgres
- pgAdmin

Important:
- Frontend sends /api requests through Nginx proxy to backend container.
- Because of that, no Vite dev server and no localhost proxy are needed in Docker mode.
- For local dev without Docker, frontend can still be started with npm run dev.

Stop all:
   docker compose down

Stop all and remove database volume:
   docker compose down -v
