CREATE INDEX idx_support_branch ON customer_support_requests(branch_id);
CREATE INDEX idx_support_customer ON customer_support_requests(customer_id);
CREATE INDEX idx_support_booking ON customer_support_requests(booking_id);
CREATE INDEX idx_support_status ON customer_support_requests(status);

CREATE INDEX idx_feedback_branch ON feedback(branch_id);
CREATE INDEX idx_feedback_booking ON feedback(booking_id);
CREATE INDEX idx_feedback_customer ON feedback(customer_id);

CREATE INDEX idx_rest_feedback_branch ON restaurant_feedback(branch_id);
CREATE INDEX idx_rest_feedback_order ON restaurant_feedback(order_id);
CREATE INDEX idx_rest_feedback_customer ON restaurant_feedback(customer_id);

CREATE INDEX idx_assignment_request ON support_assignments(request_id);
