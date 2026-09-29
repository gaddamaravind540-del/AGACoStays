CREATE INDEX IF NOT EXISTS idx_notification_customer_created ON notification_logs(customer_id, created_at DESC);
CREATE INDEX IF NOT EXISTS idx_notification_branch_created ON notification_logs(branch_id, created_at DESC);
CREATE INDEX IF NOT EXISTS idx_notification_booking ON notification_logs(booking_id);
CREATE INDEX IF NOT EXISTS idx_checkout_reminder_status_time ON checkout_reminders(status, scheduled_time);
CREATE INDEX IF NOT EXISTS idx_checkout_reminder_booking ON checkout_reminders(booking_id);
