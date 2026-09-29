# AGA CoStays - Notification Service

Base package: `com.agacostays.notification`
Database: `notification_db`
Default port: `8090`

The source architecture defines Notification Service as responsible for email, SMS, reminders, templates and logs. Its workflow is Kafka/domain request -> template resolution -> email/SMS dispatch -> retry/error handling -> notification log persistence.

## Main APIs
- `POST /api/internal/notifications/email`
- `POST /api/internal/notifications/sms`
- `GET /api/notifications/my-notifications`
- `PUT /api/notifications/{notificationId}/read`
- `POST /api/internal/checkout-reminders`
- `DELETE /api/internal/checkout-reminders/{reminderId}`
- `POST /api/root-admin/templates/email`
- `PUT /api/root-admin/templates/email/{templateId}`
- `POST /api/root-admin/templates/sms`
- `GET /api/root-admin/notifications/logs`

These endpoint paths/access rules follow the AGA CoStays APIs and Access addendum. Email/SMS provider credentials are configured in environment variables and are not hard-coded.

## Local run
1. Create PostgreSQL database `notification_db` on port 5435, or change `spring.datasource.*` in `application.properties`.
2. Set `NOTIFICATION_JWT_SECRET` to a Base64 encoded secret of at least 256 bits.
3. For real email, set the SMTP environment variables and `NOTIFICATION_EMAIL_ENABLED=true`.
4. For real SMS, set the SMS API variables and `NOTIFICATION_SMS_ENABLED=true`.
5. Start the service from STS as a Spring Boot App.

`application.properties` is intentionally used instead of YAML.

## Additional final-document notification endpoints
The final requirement document also lists booking confirmation preview/resend, checkout reminder scheduling/sending/lookup, feedback email, payment confirmation, restaurant order/food-delivered notifications and payroll salary notifications. These are implemented under `/api/notifications/...` with the listed access roles.
