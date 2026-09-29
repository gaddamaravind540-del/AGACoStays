INSERT INTO email_templates(template_code, subject, body, active)
VALUES ('BOOKING_CONFIRMATION','AGA CoStays Booking Confirmation','Hello {{customerName}}, your booking {{bookingId}} at {{hotelBranchName}} is confirmed.',TRUE)
ON CONFLICT (template_code) DO NOTHING;

INSERT INTO email_templates(template_code, subject, body, active)
VALUES ('CHECKOUT_REMINDER','AGA CoStays Checkout Reminder','Your checkout is scheduled for {{date}} at {{time}}.',TRUE)
ON CONFLICT (template_code) DO NOTHING;

INSERT INTO sms_templates(template_code, message, active)
VALUES ('PAYMENT_SUCCESS','AGA CoStays: payment received successfully.',TRUE)
ON CONFLICT (template_code) DO NOTHING;
