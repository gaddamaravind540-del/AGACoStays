CREATE INDEX idx_payment_customer ON payments(customer_id);
CREATE INDEX idx_payment_branch ON payments(branch_id);
CREATE INDEX idx_payment_reference ON payments(reference_id);
CREATE INDEX idx_payment_gateway_order ON payments(gateway_order_id);
CREATE INDEX idx_tx_payment ON payment_transactions(payment_id);
CREATE INDEX idx_refund_payment ON refunds(payment_id);
CREATE INDEX idx_webhook_received ON payment_webhook_logs(received_at);
