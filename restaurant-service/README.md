# AGA CoStays Restaurant Service

Base package: `com.agacostays.restaurant`
Database: `restaurant_db`
Port: `8087`

Configuration uses `application.properties`, `application-dev.properties` and `application-prod.properties`.

Responsibilities:
- Restaurant
- Menu
- Dish photos
- Food orders
- Kitchen workflow
- Serving workflow
- Chef/serving assignment
- Restaurant feedback

Main endpoint families:
GET /api/hotel-branches/{branchId}/restaurant
POST /api/restaurant-admin/hotel-branches/{branchId}/menu
GET /api/hotel-branches/{branchId}/restaurant/menu
POST /api/restaurant/orders
GET /api/restaurant/orders/{orderId}
GET /api/restaurant/orders/my-orders
PUT /api/restaurant-admin/orders/{orderId}/accept
PUT /api/restaurant-admin/orders/{orderId}/assign-chef
PUT /api/kitchen/orders/{orderId}/preparing
PUT /api/kitchen/orders/{orderId}/ready
PUT /api/serving/orders/{orderId}/picked-up
PUT /api/serving/orders/{orderId}/delivered

Both source-document food-order routes are supported: `/api/hotel-branches/{branchId}/restaurant/orders` and `/api/restaurant/orders?branchId=...`.
