# AGA CoStays API Gateway – API Samples

## Health

```http
GET http://localhost:8080/api/gateway/health
```

## Public city route

```http
GET http://localhost:8080/api/cities
```

## Public branch route

```http
GET http://localhost:8080/api/hotel-branches
```

## Customer booking route

```http
GET http://localhost:8080/api/bookings/my-bookings
Authorization: Bearer <ACCESS_TOKEN>
X-Trace-Id: booking-demo-001
```

## Manager analytics route

```http
GET http://localhost:8080/api/manager/analytics/branches/dashboard
Authorization: Bearer <MANAGER_ACCESS_TOKEN>
```

## Root admin route

```http
GET http://localhost:8080/api/root-admin/analytics/website
Authorization: Bearer <ROOT_ADMIN_ACCESS_TOKEN>
```

## Gateway route catalog

```http
GET http://localhost:8080/api/gateway/routes
```
