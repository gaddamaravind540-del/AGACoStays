# AGA CoStays Analytics Service

Application folder: `analytics-service`
Base package: `com.agacostays.analytics`
Database: `analytics_db`
Port: `8094`

Purpose:
Dashboards, projections, KPIs and exports.

Workflow:
Kafka events -> projection persistence -> role/branch-filtered query -> KPI/chart response
-> PDF/Excel/CSV export.

Source-defined API set:
- GET /api/analytics/hotel-dashboard
- GET /api/analytics/restaurant-dashboard
- GET /api/analytics/hotel-branches/{branchId}
- GET /api/analytics/manager-dashboard
- GET /api/analytics/root-admin-dashboard
- GET /api/analytics/revenue
- GET /api/analytics/occupancy
- GET /api/analytics/attendance
- GET /api/analytics/payroll
- POST /api/analytics/reports/export
- GET /api/analytics/reports/{reportId}

Additional report endpoints from the supplied UI/API document are also included under:
- /api/hotel-branches/{branchId}/analytics/...
- /api/manager/analytics/branches/...
- /api/root-admin/analytics/...

The service stores analytics read models locally. It does not perform cross-service SQL joins.
