# Deliveri API

Spring Boot backend for a delivery app: JWT auth, role-based order flow, restaurant search, Redis caching, Swagger.

## Requirements

- Java 17+ (for local run)
- Docker and Docker Compose (for Postgres, Redis, or full stack)
- Maven (or use the included Maven Wrapper: `./mvnw`)

## Run with Docker (full stack)

Runs the app, Postgres, and Redis in containers. App connects to `postgres:5432` and `redis:6379` via Docker network.

```bash
docker compose up --build
```

Then open **Swagger UI**: http://localhost:8080/swagger-ui.html

To run in the background: `docker compose up -d --build`

## Run locally (IDE or CLI)

If you run the app from IntelliJ or `./mvnw spring-boot:run`, Postgres and Redis must be available on `localhost`.

**If you see "Connection to localhost:5432 refused"** — Postgres (and usually Redis) are not running. Run step 1 below first, then start the app again.

1. **Start only Postgres and Redis** (leave the app out so you can run it locally):
   ```bash
   docker compose up -d postgres redis
   ```

2. **Run the application** from IDE or:
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Open Swagger UI**: http://localhost:8080/swagger-ui.html

4. **Auth**: Register via `POST /auth/register` (body: name, email, password, role: `CUSTOMER`|`RESTAURANT`|`DELIVERY`), then use the returned JWT in `Authorization: Bearer <token>` for protected endpoints.

### Seed test data

To load test data (users, restaurants, menu items, sample orders) into the database, run the app with the **dev** profile:

- **CLI**: `./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`
- **Docker**: set env `SPRING_PROFILES_ACTIVE=dev` for the app service (e.g. in `docker-compose.yml` or `docker compose run -e SPRING_PROFILES_ACTIVE=dev ...`)

Seed data is idempotent: if it has already been loaded (e.g. user `restaurant@test.com` exists), it is skipped on the next run.

**Test accounts** (password for all: `password123`):

| Email                | Role      |
|----------------------|-----------|
| restaurant@test.com  | RESTAURANT |
| customer1@test.com   | CUSTOMER  |
| customer2@test.com   | CUSTOMER  |
| delivery@test.com    | DELIVERY  |

Use these in Swagger: call `POST /auth/login` with email and password, then paste the returned JWT into **Authorize**.

## Configuration

- **Database**: Default `application.yml` uses `localhost:5432`, user/password `deliveri/deliveri`. In Docker, the app service gets `SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/deliveri` via env.
- **Redis**: Default `localhost:6379`; in Docker, `SPRING_DATA_REDIS_HOST=redis`.
- **JWT**: Set env `JWT_SECRET` in production (min 256 bits). Default in config is for development only.

## Build

```bash
./mvnw clean package
```

Run tests:

```bash
./mvnw test
```

## API Overview

- **Auth**: `POST /auth/register`, `POST /auth/login`
- **Restaurants**: `GET/POST /restaurants`, `GET /restaurants/search?name=&minRating=`, `GET /restaurants/{id}`
- **Menu**: `GET/POST/PUT/DELETE /restaurants/{restaurantId}/menu` and `.../menu/{itemId}`
- **Orders**: Create and list by role; restaurant: accept, ready; delivery: available, assign, deliver; `GET /orders/search?status=`

Roles: `CUSTOMER`, `RESTAURANT`, `DELIVERY`. Endpoints are protected with `@PreAuthorize("hasRole('...')")`.
