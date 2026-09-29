# AGA CoStays Auth Service

Application: `auth-service`  
Base package: `com.agacostays.auth`  
Database: `auth_db`  
Port: `8081`

## Responsibility

This service handles:
- Customer registration
- Login and JWT issuance
- Refresh tokens
- Logout / refresh-token revocation
- JWT validation for the API Gateway
- Password reset and password change
- OTP send/verify
- Current authenticated identity
- Login audit records
- Authentication-related Kafka events

The AGA CoStays source specifies JWT authentication, refresh tokens, password reset, OTP, logout, and validation endpoints for this service.

## Spring Tool Suite

1. Extract the ZIP.
2. STS -> File -> Import -> Maven -> Existing Maven Projects.
3. Select the `auth-service` folder.
4. Create PostgreSQL database `auth_db`.
5. Start Redis on `localhost:6379` (optional for the current persistence path).
6. Start Kafka on `localhost:9092` for event publishing.
7. Check `src/main/resources/application.properties`.
8. Run `com.agacostays.auth.AuthServiceApplication`.
9. Test `GET http://localhost:8081/actuator/health`.

## Important security notes

- Replace `JWT_SECRET` in any non-development environment.
- Do not expose `OTP_EXPOSE_IN_DEVELOPMENT=true` in production.
- The development reset-token response and development OTP response are intended for local testing only.
- The source architecture requires downstream services to validate JWT/claims again after gateway validation.
- Branch and resource ownership authorization are not stored in this auth service.

## Implementation choices

The source document defines field names and responsibilities but does not fully specify Java/database data types or all enum values for authentication token state. The ZIP therefore uses:
- PostgreSQL `BIGSERIAL` identifiers
- BCrypt password hashing
- SHA-256 hashes for persisted refresh/reset/OTP secrets
- HS256 JWT signing
- 15-minute access tokens
- 7-day refresh tokens
- 15-minute password-reset tokens
- 5-minute OTPs

These are implementation choices, not verbatim source requirements.
