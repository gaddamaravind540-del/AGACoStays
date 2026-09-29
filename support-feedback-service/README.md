# AGA CoStays Support Feedback Service

Application folder: `support-feedback-service`
Base package: `com.agacostays.support`
Database: `support_db`
Port: `8091`

Configuration uses:
- `application.properties`
- `application-dev.properties`
- `application-prod.properties`

Responsibilities:
- Customer support requests
- Support assignment/status updates
- Hotel feedback
- Restaurant feedback
- Kafka notification/analytics events
- Checkout/food-delivery event consumption

Source-defined endpoint families:

POST /api/support/requests
GET  /api/support/requests/my-requests
GET  /api/support/requests/{requestId}
PUT  /api/support/requests/{requestId}/status
POST /api/support/requests/{requestId}/assign
POST /api/feedback
GET  /api/feedback/my-feedback
GET  /api/manager/hotel-branches/{branchId}/feedback
POST /api/restaurant/feedback
GET  /api/restaurant-admin/hotel-branches/{branchId}/feedback
PUT  /api/feedback/{feedbackId}
DELETE /api/feedback/{feedbackId}
