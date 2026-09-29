# Payment API Samples

## Create booking payment
POST `http://localhost:8088/api/payments/create`

Headers:
```
Authorization: Bearer <JWT>
Content-Type: application/json
Idempotency-Key: booking-501-payment-1
```

Body:
```json
{
  "branchId": 1,
  "paymentFor": "BOOKING",
  "referenceId": "501",
  "amount": 2500.00,
  "currency": "INR",
  "paymentMethod": "RAZORPAY"
}
```

## Verify payment
POST `http://localhost:8088/api/payments/verify`
```json
{
  "gatewayOrderId": "mock_order_xxx",
  "gatewayPaymentId": "mock_payment_xxx",
  "gatewaySignature": "<HMAC-SHA256-of-orderId|paymentId>"
}
```

## Restaurant payment
POST `http://localhost:8088/api/restaurant/payments/create`
```json
{
  "branchId": 1,
  "orderId": "9001",
  "amount": 850.00,
  "currency": "INR"
}
```

## Final bill payment
POST `http://localhost:8088/api/billing/payments`
```json
{
  "branchId": 1,
  "bookingId": 501,
  "amount": 3350.00,
  "currency": "INR"
}
```

## Refund
POST `http://localhost:8088/api/payments/1001/refund`
```json
{
  "amount": 1000.00,
  "reason": "CUSTOMER_REQUEST"
}
```

For production, disable mock gateway mode and use the gateway's real signature/webhook verification.
