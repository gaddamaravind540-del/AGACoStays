# AGA CoStays Booking Service

Base package: `com.agacostays.booking`

Database: `booking_db`

Port: `8086`

This service owns the booking lifecycle: create, update, approve, reject, cancel,
check-in, check-out, booking history and booking-time price snapshot.

Configuration uses `application.properties`, `application-dev.properties`
and `application-prod.properties`.

## Main APIs

POST   /api/hotel-branches/{branchId}/bookings
POST   /api/hotel-branches/{branchId}/bookings/customer/{customerId}
GET    /api/bookings/{bookingId}
GET    /api/bookings/my-bookings
GET    /api/hotel-branches/{branchId}/bookings
PUT    /api/bookings/{bookingId}
PUT    /api/bookings/{bookingId}/approve
PUT    /api/bookings/{bookingId}/reject
PUT    /api/bookings/{bookingId}/cancel
POST   /api/bookings/{bookingId}/check-in
POST   /api/bookings/{bookingId}/check-out
GET    /api/bookings/{bookingId}/history
GET    /api/hotel-branches/{branchId}/bookings/date-range
