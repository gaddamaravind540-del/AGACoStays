# AGA CoStays Payroll Service

Application folder: `payroll-service`
Base package: `com.agacostays.payroll`
Database: `payroll_db`
Port: `8093`

Purpose: salary structures, staff bank accounts, payroll generation, salary credits,
payment status/retry, salary slips and payroll reports.

Workflow:
Month payroll request -> active staff -> attendance summary -> salary structure ->
gross/deductions/net -> payroll batch -> salary credit/slip event.

Create DB:
`CREATE DATABASE payroll_db;`

Swagger:
`http://localhost:8093/swagger-ui.html`
