# AGA CoStays API Gateway Service

Base package: `com.agacostays.gateway`

Application: `api-gateway-service`

Port: `8080`

Database: none

## Responsibility

The gateway is the single external entry point. It provides:
- Request routing to downstream services
- JWT validation at the edge
- Coarse role-based route validation
- Trace/correlation ID propagation
- CORS
- Redis-backed rate limiting
- Consistent gateway error responses
- Health and route inspection endpoints

The downstream services must still validate JWT claims and enforce detailed permission, branch-scope and resource-ownership rules.

## Spring versions

This implementation uses Java 21, Spring Boot 4.1.1 and Spring Cloud 2025.1.3.

## Local setup in Spring Tool Suite

1. Extract the project.
2. In STS choose `File -> Import -> Maven -> Existing Maven Projects`.
3. Select the `api-gateway-service` folder.
4. Start Redis on `localhost:6379`.
5. Start the AGA CoStays downstream services on the URLs configured in `application.properties`.
6. Run `ApiGatewayApplication`.

## Important environment variables

- `JWT_SECRET`
- `REDIS_HOST`
- `REDIS_PORT`
- `AUTH_SERVICE_URL`
- `USER_SERVICE_URL`
- `BRANCH_SERVICE_URL`
- `ROOM_SERVICE_URL`
- `BOOKING_SERVICE_URL`
- `RESTAURANT_SERVICE_URL`
- `PAYMENT_SERVICE_URL`
- `BILLING_SERVICE_URL`
- `NOTIFICATION_SERVICE_URL`
- `ATTENDANCE_SERVICE_URL`
- `PAYROLL_SERVICE_URL`
- `ANALYTICS_SERVICE_URL`
- `SUPPORT_SERVICE_URL`
- `CORS_ALLOWED_ORIGINS`

## Gateway endpoints

- `GET /actuator/health`
- `GET /api/gateway/health`
- `GET /api/gateway/routes`

## Notes

The supplied AGA CoStays architecture marks the API Gateway as having no database, while its completeness checklist also lists `db/migration/V1__...`, `V2__...`, and `V3__...`. Those SQL files are included here as no-op placeholders rather than creating a gateway database.
