# AGA CoStays Billing Service

Application folder: `billing-service`  
Base package: `com.agacostays.billing`  
Database: `billing_db`  
Port: `8089`

Purpose: final bills, invoices, room charges and restaurant charges.

Workflow: Checkout/order event -> collect room and restaurant charges -> tax/discount calculation -> bill persistence -> invoice generation -> payment status.

Main APIs:
- POST /api/billing/bookings/{bookingId}/generate
- GET /api/billing/bookings/{bookingId}
- GET /api/billing/{billId}
- POST /api/billing/{billId}/discount
- POST /api/billing/{billId}/pay
- GET /api/billing/{billId}/charges
- POST /api/invoices/bills/{billId}
- GET /api/invoices/{invoiceId}
- GET /api/invoices/{invoiceId}/download
- GET /api/manager/hotel-branches/{branchId}/bills

UI aliases included: GET /api/billing/my-bill/{bookingId}, POST /api/billing/final-bill/{bookingId}/pay.
