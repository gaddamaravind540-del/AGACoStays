# Billing API Samples

POST /api/billing/bookings/501/generate
```json
{"branchId":1,"customerId":1001}
```

POST /api/billing/10/discount
```json
{"discountType":"PERCENTAGE","value":10,"reason":"Manager approved discount"}
```

POST /api/billing/10/pay
```json
{"paymentMethod":"UPI"}
```

POST /api/invoices/bills/10
GET /api/invoices/20
GET /api/invoices/20/download
GET /api/manager/hotel-branches/1/bills?page=0&size=10
