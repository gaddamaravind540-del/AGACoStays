# AGA CoStays Attendance Service

Application folder: `attendance-service`  
Base package: `com.agacostays.attendance`  
Database: `attendance_db`  
Port: `8092`

Purpose: staff attendance, check-in/out, corrections and reports.

Workflow:
Staff check-in/out -> user and branch validation -> duplicate validation
-> attendance persistence -> monthly projection -> Kafka event.

Source-derived API set implemented:
- POST /api/attendance/check-in
- POST /api/attendance/check-out
- GET /api/attendance/my-attendance
- POST /api/manager/staff/{staffId}/attendance
- PUT /api/manager/attendance/{attendanceId}
- GET /api/manager/hotel-branches/{branchId}/attendance
- GET /api/manager/hotel-branches/{branchId}/working-staff
- GET /api/manager/hotel-branches/{branchId}/absent-staff
- GET /api/attendance/staff/{staffId}/monthly

Compatibility routes from the earlier UI/API document are also implemented:
- POST /api/staff/my-branch/attendance/check-in
- POST /api/staff/my-branch/attendance/check-out
- POST /api/attendance/mark
- POST /api/attendance/mark-absent
- GET /api/hotel-branches/{branchId}/attendance/today/working
- GET /api/hotel-branches/{branchId}/attendance/today/absent
- GET /api/hotel-branches/{branchId}/attendance/monthly
- GET /api/attendance/staff/{staffId}
- PUT /api/attendance/{attendanceId}
- DELETE /api/attendance/{attendanceId}

Run:
1. Create PostgreSQL database `attendance_db`.
2. Update `application.properties` credentials if required.
3. Start Kafka on localhost:9092 for event publishing.
4. In STS, run AttendanceServiceApplication as Spring Boot App.
5. Swagger: http://localhost:8092/swagger-ui.html
