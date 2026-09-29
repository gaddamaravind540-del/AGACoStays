# AGA CoStays Analytics API Samples

## Hotel dashboard
GET `/api/analytics/hotel-dashboard`

## Restaurant dashboard
GET `/api/analytics/restaurant-dashboard`

## Branch analytics
GET `/api/analytics/hotel-branches/1`

## Manager dashboard
GET `/api/analytics/manager-dashboard`

## Root admin dashboard
GET `/api/analytics/root-admin-dashboard`

## Export report
POST `/api/analytics/reports/export`
```json
{
  "reportType": "REVENUE",
  "format": "EXCEL",
  "branchId": 1,
  "fromDate": "2026-09-01",
  "toDate": "2026-09-29"
}
```

## Report status
GET `/api/analytics/reports/1`

## Download report
GET `/api/analytics/reports/1/download`

## Branch hotel dashboard
GET `/api/hotel-branches/1/analytics/hotel/dashboard`

## Branch restaurant dashboard
GET `/api/hotel-branches/1/analytics/restaurant/dashboard`

## Branch overall revenue
GET `/api/hotel-branches/1/analytics/overall/revenue`
