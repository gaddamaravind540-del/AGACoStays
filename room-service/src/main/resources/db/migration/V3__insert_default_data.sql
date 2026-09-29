INSERT INTO rooms (branch_id, room_number, room_type, base_price_per_day, current_price_per_day, description, floor, status, created_by, updated_by)
VALUES
    (1, 'B101', 'NORMAL', 1000.00, 1000.00, 'Standard room', 1, 'AVAILABLE', 1, 1),
    (1, 'B201', 'DELUXE', 1600.00, 1800.00, 'Semi luxury room with bed, washroom, kitchen, AC, TV and fridge', 2, 'AVAILABLE', 1, 1),
    (1, 'B301', 'LUXURY', 3000.00, 3000.00, 'Luxury room', 3, 'AVAILABLE', 1, 1)
ON CONFLICT (branch_id, room_number) DO NOTHING;
