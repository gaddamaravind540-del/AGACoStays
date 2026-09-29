# AGA CoStays Auth Service – API Samples

Base URL: `http://localhost:8081`

## 1. Customer registration

```http
POST /api/auth/register
Content-Type: application/json
X-Trace-Id: reg-001

{
  "fullName": "John Customer",
  "email": "john@example.com",
  "phone": "9876543210",
  "password": "Customer@123"
}
```

## 2. Login

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "Customer@123"
}
```

## 3. Refresh token

```http
POST /api/auth/refresh-token
Content-Type: application/json

{
  "refreshToken": "<REFRESH_TOKEN>"
}
```

## 4. Validate JWT for Gateway/System

```http
POST /api/auth/validate-token
Content-Type: application/json

{
  "token": "<ACCESS_TOKEN>"
}
```

## 5. Current identity

```http
GET /api/auth/me
Authorization: Bearer <ACCESS_TOKEN>
```

## 6. Logout

```http
POST /api/auth/logout
Authorization: Bearer <ACCESS_TOKEN>
Content-Type: application/json

{
  "refreshToken": "<REFRESH_TOKEN>"
}
```

## 7. Forgot password

```http
POST /api/auth/forgot-password
Content-Type: application/json

{
  "email": "john@example.com"
}
```

## 8. Reset password

```http
POST /api/auth/reset-password
Content-Type: application/json

{
  "resetToken": "<RESET_TOKEN>",
  "newPassword": "NewPassword@123"
}
```

## 9. Change password

```http
PUT /api/auth/change-password
Authorization: Bearer <ACCESS_TOKEN>
Content-Type: application/json

{
  "currentPassword": "Customer@123",
  "newPassword": "NewPassword@123"
}
```

## 10. Send OTP

```http
POST /api/auth/otp/send
Content-Type: application/json

{
  "destination": "john@example.com",
  "purpose": "PASSWORD_RESET"
}
```

## 11. Verify OTP

```http
POST /api/auth/otp/verify
Content-Type: application/json

{
  "destination": "john@example.com",
  "otp": "123456",
  "purpose": "PASSWORD_RESET"
}
```

## 12. Root Admin login

```http
POST /api/root-admin/auth/login
Content-Type: application/json

{
  "email": "root@example.com",
  "password": "<ROOT_ADMIN_PASSWORD>"
}
```

## Note

`developmentOtp` and the password reset token are intentionally visible in local responses to make STS/Postman testing possible. Production notification delivery should use the Notification Service.
