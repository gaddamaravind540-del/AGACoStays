# Payroll API samples

### Create salary structure
POST `/api/manager/payroll/salary-structures`
```json
{"roleName":"STAFF","basicSalary":20000,"hra":4000,"allowances":2000,"deductions":1000,"effectiveFrom":"2026-09-01","currency":"INR"}
```

### Add staff bank account
POST `/api/manager/payroll/staff/25/bank-account`
```json
{"accountHolderName":"Test Staff","bankName":"Demo Bank","accountNumber":"123456789012","ifscCode":"DEMO0001234","branchName":"Main Branch","accountType":"SAVINGS"}
```

### Generate staff payroll
POST `/api/manager/payroll/staff/25/generate`
```json
{"branchId":1,"month":9,"year":2026,"paymentMode":"BANK_TRANSFER"}
```

### Search payroll
GET `/api/manager/payroll?month=9&year=2026&page=0&size=20`

### Credit salaries
POST `/api/manager/payroll/credit-salaries?month=9&year=2026`
```json
{"paymentMode":"BANK_TRANSFER"}
```

### Salary status
GET `/api/manager/payroll/1/payment-status`

### Salary slip
POST `/api/manager/payroll/1/salary-slip`
GET `/api/manager/payroll/1/salary-slip`
