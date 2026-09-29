# AGA CoStays Attendance API Samples

## Staff self check-in
POST `/api/attendance/check-in`
```json
{
  "branchId": 1,
  "deviceId": "DESKTOP-01",
  "latitude": 12.9716,
  "longitude": 77.5946
}
```

## Staff self check-out
POST `/api/attendance/check-out`
```json
{
  "branchId": 1
}
```

## Manager manual attendance
POST `/api/manager/staff/25/attendance`
```json
{
  "branchId": 1,
  "staffId": 25,
  "attendanceDate": "2026-09-29",
  "status": "PRESENT",
  "checkInTime": "09:30:00",
  "checkOutTime": "18:00:00",
  "leaveType": "NONE",
  "remarks": "Regular shift"
}
```

## Mark absent
POST `/api/attendance/mark-absent`
```json
{
  "branchId": 1,
  "staffId": 25,
  "attendanceDate": "2026-09-29",
  "status": "ABSENT",
  "remarks": "Absent"
}
```

## Correct attendance
PUT `/api/manager/attendance/1`
```json
{
  "requestedStatus": "PRESENT",
  "requestedCheckIn": "09:00:00",
  "requestedCheckOut": "18:00:00",
  "reason": "Corrected by manager"
}
```

## Own attendance
GET `/api/attendance/my-attendance`

## Staff monthly attendance
GET `/api/attendance/staff/25/monthly?month=9&year=2026`

## Branch working staff
GET `/api/manager/hotel-branches/1/working-staff`

## Branch absent staff
GET `/api/manager/hotel-branches/1/absent-staff`

## Branch attendance
GET `/api/manager/hotel-branches/1/attendance?page=0&size=20`
