# SafeTrip Frontend

Frontend for the SafeTrip project. This part of the system is made for tourists visiting Almaty and helps them browse tours, food places, entertainment spots, and basic safety information.

## What is included

- home page with the main project presentation
- tours page with cards and detailed tour view
- food page based on place data from backend
- entertainment page based on place data from backend
- security page with useful practical information
- login and registration pages connected to backend auth
- favorites for authorized users

## Stack

- React
- Vite
- React Router
- Axios

## Run the frontend

Open a terminal in `safetrip-front` and run:

```bash
npm install
npm run dev
```

By default the app runs on:

```text
http://localhost:5173
```

## Backend connection

The frontend works with the backend from the root `tourGit` project.

Main API routes used by the frontend:

- `/api/auth/login`
- `/api/auth/register`
- `/api/tours`
- `/api/tours/:id`
- `/api/places`
- `/api/places/:id`
- `/api/profile/favorites`

Vite proxy is configured for local development, so requests from the frontend can go to the backend running on:

```text
http://localhost:8080
```

## Main pages

- `/` home page
- `/tours` tours list
- `/tours/:id` tour details
- `/food` food places
- `/entertainment` entertainment places
- `/places/:id` place details
- `/security` safety information
- `/login` login page
- `/register` registration page

## Notes

- If images from backend seed data do not load, the frontend uses fallback images.
- Favorites work only after login.
- Public lists like tours and places can be opened without authorization.
