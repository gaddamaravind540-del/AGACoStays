INSERT INTO cities(city_name,state,country,status)
VALUES ('Bengaluru','Karnataka','India','ACTIVE'),
       ('Chennai','Tamil Nadu','India','ACTIVE'),
       ('Mumbai','Maharashtra','India','ACTIVE')
ON CONFLICT(city_name) DO NOTHING;

INSERT INTO hotel_branches(city_id,branch_name,address,phone,status)
SELECT city_id,'AGA CoStays Bengaluru','Bengaluru, Karnataka','0800000000','ACTIVE'
FROM cities WHERE city_name='Bengaluru'
ON CONFLICT(branch_name) DO NOTHING;

INSERT INTO hotel_branches(city_id,branch_name,address,phone,status)
SELECT city_id,'AGA CoStays Chennai','Chennai, Tamil Nadu','0440000000','ACTIVE'
FROM cities WHERE city_name='Chennai'
ON CONFLICT(branch_name) DO NOTHING;

INSERT INTO hotel_branches(city_id,branch_name,address,phone,status)
SELECT city_id,'AGA CoStays Mumbai','Mumbai, Maharashtra','0220000000','ACTIVE'
FROM cities WHERE city_name='Mumbai'
ON CONFLICT(branch_name) DO NOTHING;
