# AGA CoStays Payment Service

Payment service for payment creation, verification, gateway webhook processing, refunds and payment events.

## Runtime
- Java 21
- Spring Boot 4.1.1
- PostgreSQL database: `payment_db`
- Default local port: `8088`
- Swagger UI: `http://localhost:8088/swagger-ui.html`

## Main API families
- `/api/payments/*`
- `/api/restaurant/payments/*`
- `/api/billing/payments`

The implementation uses a gateway adapter with a mock mode for local development. Production deployments should disable mock mode and provide the real gateway credentials. Webhook/gateway status is the authoritative source for the final payment state.

See `PAYMENT-SERVICE-COPY-PASTE.txt` for the complete file tree and setup notes.
