CREATE INDEX IF NOT EXISTS idx_reports_status ON reports(status);
CREATE INDEX IF NOT EXISTS idx_reports_requester ON reports(requested_by);
CREATE INDEX IF NOT EXISTS idx_hotel_revenue_date ON hotel_revenue_projection(metric_date);
CREATE INDEX IF NOT EXISTS idx_restaurant_sales_date ON restaurant_sales_projection(metric_date);
CREATE INDEX IF NOT EXISTS idx_attendance_projection_date ON attendance_projection(metric_date);
CREATE INDEX IF NOT EXISTS idx_payroll_projection_date ON payroll_projection(metric_date);
CREATE INDEX IF NOT EXISTS idx_feedback_projection_date ON feedback_projection(metric_date);
