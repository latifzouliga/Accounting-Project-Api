insert into roles(created_date, created_by, is_deleted, last_modified,
                  last_modified_by, description)
values ('2022-09-09 00:00:00', 'root@cydeo.com', false, '2022-09-09 00:00:00', 'root@cydeo.com', 'Root User'),
       ('2022-09-09 00:00:00', 'root@cydeo.com', false, '2022-09-09 00:00:00', 'root@cydeo.com', 'Admin'),
       ('2022-09-09 00:00:00', 'root@cydeo.com', false, '2022-09-09 00:00:00', 'root@cydeo.com', 'Manager'),
       ('2022-09-09 00:00:00', 'root@cydeo.com', false, '2022-09-09 00:00:00', 'root@cydeo.com', 'Employee');

insert into addresses(created_date, created_by, is_deleted, last_modified, last_modified_by,
                      address_line1, address_line2, city, state, country, zip_code)
values ('2022-09-15 00:00:00', 'root@cydeo.com', false, '2022-09-15 00:00:00', 'root@cydeo.com',
        '7925 Jones Branch Dr, #3300', 'Tysons', 'Virginia', 'VA', 'United States', '22102-1234'),
       ('2022-09-15 00:00:00', 'root@cydeo.com', false, '2022-09-15 00:00:00', 'root@cydeo.com',
        'Future Street', 'Times Square', 'Atlanta', 'Alabama', 'United States', '54321-4321'),
       ('2022-09-15 00:00:00', 'root@cydeo.com', false, '2022-09-15 00:00:00', 'root@cydeo.com',
        'North Street', 'Circle Square', 'San Francisco', 'California', 'United States', '65245-8546'),
       ('2022-09-15 00:00:00', 'root@cydeo.com', false, '2022-09-15 00:00:00', 'root@cydeo.com',
        'West Street', 'Triangle Square', 'Los Angeles', 'California', 'United States', '54782-5214'),
       ('2022-09-15 00:00:00', 'root@cydeo.com', false, '2022-09-15 00:00:00', 'root@cydeo.com',
        'East Street', 'Cube Square', 'Los Angeles', 'California', 'United States', '54782-5214'),
       ('2022-09-15 00:00:00', 'root@cydeo.com', false, '2022-09-15 00:00:00', 'root@cydeo.com',
        'South Street', 'Times Square', 'Los Angeles', 'California', 'United States', '54782-5214'),
       ('2022-09-15 00:00:00', 'root@cydeo.com', false, '2022-09-15 00:00:00', 'root@cydeo.com',
        'SouthWest Street', 'Puzzle Square', 'Los Angeles', 'California', 'United States', '65654-8989'),
       ('2022-09-15 00:00:00', 'root@cydeo.com', false, '2022-09-15 00:00:00', 'root@cydeo.com',
        'OwerWest Street', 'Android Square', 'Los Angeles', 'Phoneix', 'United States', '65654-8989');;

insert into companies(created_date, created_by, is_deleted, last_modified, last_modified_by,
                      title, phone, website, address_id, company_status)
values ('2022-09-15 00:00:00', 'root@cydeo.com', false, '2022-09-15 00:00:00', 'root@cydeo.com',
        'CYDEO','+1 (652) 852-8888', 'https://www.cydeo.com', 1, 'ACTIVE'),
       ('2022-09-15 00:00:00', 'root@cydeo.com', false, '2022-09-15 00:00:00', 'root@cydeo.com',
        'Green Tech','+1 (652) 852-3246', 'https://www.greentech.com', 2, 'ACTIVE'),
       ('2022-09-15 00:00:00', 'root@cydeo.com', false, '2022-09-15 00:00:00', 'root@cydeo.com',
        'Blue Tech','+1 (215) 654-5268', 'https://www.bluetech.com', 3, 'ACTIVE'),
       ('2022-09-15 00:00:00', 'root@cydeo.com', false, '2022-09-15 00:00:00', 'root@cydeo.com',
        'Red Tech','+1 (215) 846-2642', 'https://www.redtech.com', 4, 'PASSIVE');

insert into users(created_date, created_by, is_deleted, last_modified, last_modified_by,
                  username, password, firstname, lastname, phone, role_id, company_id, enabled)
values
-- COMPANY-1 / CYDEO / ROOT USER
('2022-09-09 00:00:00', 'root@cydeo.com', false, '2022-09-09 00:00:00', 'root@cydeo.com',
 'root@cydeo.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
 'Robert', 'Martin', '+1 (852) 564-5874', 1, 1, true),
-- COMPANY-2 / Green Tech / ADMIN-1
('2022-09-09 00:00:00', 'root@cydeo.com', false, '2022-09-09 00:00:00', 'root@cydeo.com',
 'admin@greentech.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
 'Mary', 'Grant', '+1 (234) 345-4362', 2, 2, true),
-- COMPANY-2 / Green Tech / ADMIN-2
('2022-09-09 00:00:00', 'root@cydeo.com', false, '2022-09-09 00:00:00', 'root@cydeo.com',
 'admin2@greentech.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
 'Garrison', 'Short', '+1 (234) 356-7865', 2, 2, true),
-- COMPANY-2 / Green Tech / MANAGER
('2022-09-09 00:00:00', 'root@cydeo.com', false, '2022-09-09 00:00:00', 'root@cydeo.com',
 'manager@greentech.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
 'Robert', 'Noah', '+1 (234) 564-5874', 3, 2, true),
-- COMPANY-2 / Green Tech / EMPLOYEE
('2022-09-09 00:00:00', 'root@cydeo.com', false, '2022-09-09 00:00:00', 'root@cydeo.com',
 'employee@greentech.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
 'Mike', 'Times', '+1 (234) 741-8569', 4, 2, true),
-- COMPANY-3 / Blue Tech / ADMIN
('2022-09-09 00:00:00', 'root@cydeo.com', false, '2022-09-09 00:00:00', 'root@cydeo.com',
 'admin@bluetech.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
 'Chris', 'Brown', '+1 (356) 258-3544', 2, 3, true),
-- COMPANY-3 / Blue Tech / MANAGER
('2022-09-09 00:00:00', 'root@cydeo.com', false, '2022-09-09 00:00:00', 'root@cydeo.com',
 'manager@bluetech.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
 'Tom', 'Hanks', '+1 (356) 258-3544', 3, 3, true),
-- COMPANY-4 / Red Tech / ADMIN
('2022-09-09 00:00:00', 'root@cydeo.com', false, '2022-09-09 00:00:00', 'root@cydeo.com',
 'admin@redtech.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
 'John', 'Doe', '+1 (659) 756-1265', 2, 4, true),
-- ///////////////////

('2022-09-09 00:00:00', 'root@cydeo.com', false, '2022-09-09 00:00:00', 'root@cydeo.com',
 'admin2@bluetech.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
 'zouliga', 'Brown', '+1 (356) 258-3544', 2, 3, true),
('2022-09-09 00:00:00', 'root@cydeo.com', false, '2022-09-09 00:00:00', 'root@cydeo.com',
    'manager2@bluetech.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
    'Tom and jerry', 'Hanks', '+1 (356) 258-3544', 3, 3, true),
('2022-09-09 00:00:00', 'root@cydeo.com', false, '2022-09-09 00:00:00', 'root@cydeo.com',
 'employee4@bluetech.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
 'Mike', 'Hanks', '+1 (356) 258-3544', 4, 3, true),
('2022-09-09 00:00:00', 'root@cydeo.com', false, '2022-09-09 00:00:00', 'root@cydeo.com',
 'employee3@bluetech.com', '$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK',
 'Zaid', 'Hanks', '+1 (356) 258-3544', 4, 3, true);

insert into clients_vendors(created_date, created_by, is_deleted, last_modified, last_modified_by,
                            client_vendor_type, client_vendor_name, phone, website, address_id, company_id)
values
-- COMPANY-2 / Green Tech
('2022-09-15T00:00','admin@greentech.com', false,'2022-09-15T00:00','admin@greentech.com','CLIENT','Orange Tech', '+1 (251) 321-4155', 'https://www.orange.com', 5, 2),
('2022-09-15T00:00','admin@greentech.com', false,'2022-09-15T00:00','admin@greentech.com','CLIENT','Ower Tech', '+1 (251) 321-4141', 'https://www.ower.com', 8, 2),
('2022-09-15T00:00','admin@greentech.com', false,'2022-09-15T00:00','admin@greentech.com','VENDOR','Photobug Tech', '+1 (652) 852-3246', 'https://www.photobug.com', 6, 2),
('2022-09-15T00:00','admin@greentech.com', false,'2022-09-15T00:00','admin@greentech.com','VENDOR','Wordtune Tech','+1 (652) 852-3246','https://www.wordtune.com', 7, 2),
-- COMPANY-3 / Blue Tech
('2022-09-15T00:00','admin@bluetech.com', false,'2022-09-15T00:00','admin@bluetech.com','CLIENT', 'Reallinks Tech', '+1 (652) 852-9544','https://www.reallinks.com', 3, 3),
('2022-09-15T00:00','admin@bluetech.com', false,'2022-09-15T00:00','admin@bluetech.com','VENDOR', 'Livetube Tech', '+1 (652) 852-2055','https://www.livetube.com', 4, 3),
('2022-09-15T00:00','admin@bluetech.com', false,'2022-09-15T00:00','admin@bluetech.com','CLIENT', 'Key Tech', '+1 (652) 852-7896','https://www.keytech.com', 1, 3),
('2022-09-15T00:00','admin@bluetech.com', false,'2022-09-15T00:00','admin@bluetech.com','VENDOR', 'Mod Tech', '+1 (652) 852-3648','https://www.modtech.com', 2, 3);


insert into categories(created_date, created_by, is_deleted, last_modified, last_modified_by,
                       description, company_id)
values
-- COMPANY-2 / Green Tech
('2022-09-15 00:00:00', 'admin@greentech.com', false, '2022-09-15 00:00:00', 'admin@greentech.com', 'Computer', 2),
('2022-09-15 00:00:00', 'admin@greentech.com', false, '2022-09-15 00:00:00', 'admin@greentech.com', 'Phone', 2),
-- COMPANY-3 / Blue Tech
('2022-09-15 00:00:00', 'admin@bluetech.com', false, '2022-09-15 00:00:00', 'admin@bluetech.com', 'Phone', 3),
('2022-09-15 00:00:00', 'admin@bluetech.com', false, '2022-09-15 00:00:00', 'admin@bluetech.com', 'TV', 3),
('2022-09-15 00:00:00', 'admin@bluetech.com', false, '2022-09-15 00:00:00', 'admin@bluetech.com', 'Monitor', 3);

insert into products(created_date, created_by, is_deleted, last_modified, last_modified_by,
                     name, quantity_in_stock, low_limit_alert, product_unit, category_id)
VALUES
-- COMPANY-2 / Green Tech
('2022-09-15 00:00', 'admin@greentech.com', 'false', '2022-09-15 00:00', 'admin@greentech.com', 'HP Elite 800G1 Desktop Computer Package', 8, 5,'PCS', 1),
('2022-09-15 00:00', 'admin@greentech.com', 'false', '2022-09-15 00:00', 'admin@greentech.com', '2021 Apple MacBook Pro', 0, 5,'PCS', 1),
('2022-09-15 00:00', 'admin@greentech.com', 'false', '2022-09-15 00:00', 'admin@greentech.com', 'Apple iPhone-13', 0, 5,'PCS', 2),
('2022-09-15 00:00', 'admin@greentech.com', 'false', '2022-09-15 00:00', 'admin@greentech.com', 'SAMSUNG Galaxy S22',0, 5,'PCS', 2),
-- COMPANY-3 / Blue Tech
('2022-09-15 00:00', 'admin@bluetech.com', 'false', '2022-09-15 00:00', 'admin@bluetech.com', 'Samsung Galaxy S20 (renewed)', 30, 5, 'PCS', 3),
('2022-09-15 00:00', 'admin@bluetech.com', 'false', '2022-09-15 00:00', 'admin@bluetech.com', 'Samsung Galaxy S22', 20, 5, 'PCS', 3),
('2022-09-15 00:00', 'admin@bluetech.com', 'false', '2022-09-15 00:00', 'admin@bluetech.com', 'Moto G Power', 0, 5, 'PCS', 3);


insert into invoices(created_date, created_by, is_deleted, last_modified, last_modified_by,
                     date,invoice_no, invoice_type, invoice_status, client_vendor_id, company_id)
values
-- COMPANY-2 / Green Tech
('2022-09-09 00:00', 'admin@greentech.com', 'false', '2022-09-09 00:00', 'admin@greentech.com', '2022-09-09', 'P-001', 'PURCHASE', 'APPROVED', 2, 2),
('2022-09-10 00:00', 'admin@greentech.com', 'false', '2022-09-10 00:00', 'admin@greentech.com', '2022-09-10', 'P-002', 'PURCHASE', 'APPROVED', 3, 2),
('2022-09-17 00:00', 'admin@greentech.com', 'false', '2022-09-17 00:00', 'admin@greentech.com', '2022-09-17', 'S-001', 'SALES', 'APPROVED', 1, 2),
('2022-10-19 00:00', 'admin@greentech.com', 'false', '2022-10-19 00:00', 'admin@greentech.com', '2022-10-19', 'S-002', 'SALES', 'AWAITING_APPROVAL', 1, 2),
('2022-11-20 00:00', 'admin@greentech.com', 'false', '2022-11-20 00:00', 'admin@greentech.com', '2022-11-20', 'S-003', 'SALES', 'AWAITING_APPROVAL', 1, 2),

-- COMPANY-3 / Blue Tech
('2022-09-09 00:00', 'admin@bluetech.com', 'false', '2022-09-09 00:00', 'admin@bluetech.com', '2022-09-09', 'P-001', 'PURCHASE', 'APPROVED', 5, 3),
('2022-09-10 00:00', 'admin@bluetech.com', 'false', '2022-09-10 00:00', 'admin@bluetech.com', '2022-09-10', 'P-002', 'PURCHASE', 'APPROVED', 5, 3),
('2022-09-13 00:00', 'admin@bluetech.com', 'false', '2022-09-13 00:00', 'admin@bluetech.com', '2022-09-13', 'S-001', 'SALES', 'APPROVED', 4, 3),
('2022-11-18 00:00', 'admin@bluetech.com', 'false', '2022-11-18 00:00', 'admin@bluetech.com', '2022-11-18', 'S-002', 'SALES', 'AWAITING_APPROVAL', 4, 3),
('2022-11-19 00:00', 'admin@bluetech.com', 'false', '2022-11-19 00:00', 'admin@bluetech.com', '2022-11-19', 'S-003', 'SALES', 'AWAITING_APPROVAL', 4, 3),
('2022-11-20 00:00', 'admin@bluetech.com', 'false', '2022-11-20 00:00', 'admin@bluetech.com', '2022-11-20', 'S-004', 'SALES', 'AWAITING_APPROVAL', 6, 3),
('2022-11-21 00:00', 'admin@bluetech.com', 'false', '2022-11-21 00:00', 'admin@bluetech.com', '2022-11-21', 'S-005', 'SALES', 'AWAITING_APPROVAL', 6, 3),
('2022-12-15 00:00', 'admin@bluetech.com', 'false', '2022-12-15 00:00', 'admin@bluetech.com', '2022-12-15', 'P-003', 'PURCHASE', 'AWAITING_APPROVAL', 6, 3);
--
insert into invoice_products(created_date, created_by, is_deleted, last_modified, last_modified_by,
                             price,quantity, remaining_quantity, tax, profit_loss, invoice_id, product_id)
values
-- COMPANY-2 / Green Tech
('2022-09-15 00:00', 'admin@greentech.com', 'false', '2022-09-15 00:00', 'admin@greentech.com', 250, 5, 3, 10, 0, 1, 1),     --purchase APPROVED
('2022-09-15 00:00', 'admin@greentech.com', 'false', '2022-09-15 00:00', 'admin@greentech.com', 250, 5, 5, 10, 0, 2, 1),     --purchase APPROVED total cost (with tax) 2750
('2022-09-15 00:00', 'admin@greentech.com', 'false', '2022-09-15 00:00', 'admin@greentech.com', 300, 2, 0, 10, 110, 3, 1),    --sale APPROVED     total sale (with tax) 660 & profit : 110 with tax
('2022-09-15 00:00', 'admin@greentech.com', 'false', '2022-09-15 00:00', 'admin@greentech.com', 200, 2, 0, 10, 0, 4, 1),     --sale AWAITING_APPROVAL after approval total sale (with tax) :1100 & profit (with tax) : 0
('2022-09-15 00:00', 'admin@greentech.com', 'false', '2022-09-15 00:00', 'admin@greentech.com', 300, 5, 0, 10, 0, 5, 1),      --sale AWAITING_APPROVAL after approval total sale (with tax) :2750 & profit (with tax) : 275

-- COMPANY-3 / Blue Tech
('2022-09-15 00:00', 'admin@bluetech.com', 'false', '2022-09-15 00:00', 'admin@bluetech.com', 200, 20, 0, 10, 0, 6, 5),     --purchase APPROVED
('2022-09-15 00:00', 'admin@bluetech.com', 'false', '2022-09-15 00:00', 'admin@bluetech.com', 200, 20, 10, 10, 0, 6, 5),    --purchase APPROVED
('2022-09-15 00:00', 'admin@bluetech.com', 'false', '2022-09-15 00:00', 'admin@bluetech.com', 900, 10, 10, 10, 0, 7, 6),    --purchase APPROVED
('2022-09-15 00:00', 'admin@bluetech.com', 'false', '2022-09-15 00:00', 'admin@bluetech.com', 1000, 10, 10, 10, 0, 7, 6),  --purchase APPROVED  total cost (with tax) 29700
('2022-09-15 00:00', 'admin@bluetech.com', 'false', '2022-09-15 00:00', 'admin@bluetech.com', 300, 10, 0, 10, 1100, 8, 5),  --sale APPROVED     total sale (with tax) 3300 & profit : 1100 with tax
('2022-09-15 00:00', 'admin@bluetech.com', 'false', '2022-09-15 00:00', 'admin@bluetech.com', 300, 20, 0, 10, 2200, 8, 5),  --sale APPROVED     total sale (with tax): 9900 & total profit : 3300 with tax
('2022-09-15 00:00', 'admin@bluetech.com', 'false', '2022-09-15 00:00', 'admin@bluetech.com', 1200, 6, 0, 10, 0, 9, 6),     --sale AWAITING_APPROVAL after approval total sale (with tax) : 17820 & profit (with tax) : 3300+1980=5280
('2022-09-15 00:00', 'admin@bluetech.com', 'false', '2022-09-15 00:00', 'admin@bluetech.com', 1200, 5, 0, 10, 0, 10, 6),     --sale AWAITING_APPROVAL after approval total sale (with tax) : 24420 & profit (with tax) : 5280+1540=6820
('2022-09-15 00:00', 'admin@bluetech.com', 'false', '2022-09-15 00:00', 'admin@bluetech.com', 1200, 2, 0, 10, 0, 11, 6),    --sale AWAITING_APPROVAL after approval total sale (with tax) : 27060 & profit (with tax) : 6820+440=7260
('2022-12-15 00:00', 'admin@bluetech.com', 'false', '2022-12-15 00:00', 'admin@bluetech.com', 600, 4, 0, 10, 0, 13, 7);    --purchase AWAITING_APPROVAL

-- -- Insert sample data
-- INSERT INTO payments (created_date, created_by, last_modified_by, is_deleted, last_modified, year, amount,payment_date, is_paid,   company_stripe_id, month, company_id)
-- VALUES
--     -- Company 1
--     (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2022, 100.50, '2022-03-01', true, 'stripe_id_123', 'MARCH', 1),
--     (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2023, 150.75, '2023-04-05', true, 'stripe_id_456', 'APRIL', 1),
--     (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2024, 80.00, '2024-05-10', false, 'stripe_id_789', 'MAY', 1),
--     (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2025, 120.00, '2016-06-15', true, 'stripe_id_234', 'JUNE', 1),
--     (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2026, 200.25, '2016-07-20', false, 'stripe_id_567', 'JULY', 1),
--     (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2027, 90.80, '2020-08-25', true, 'stripe_id_890', 'AUGUST', 1),
--     -- Company 2
--
--     (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2028, 110.00, '2028-09-30', true,'truripe_id_345', 'SEPTEMBER', 3),
--     (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2022, 110.50, '2022-03-02', true, 'stripe_id_123', 'MARCH', 2),
--     (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2023, 160.75, '2023-04-06', true, 'stripe_id_456', 'APRIL', 2),
--     (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2024, 85.00, '2024-05-11', false, 'stripe_id_789', 'MAY', 2),
--     (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2025, 125.00, '2016-06-16', true, 'stripe_id_234', 'JUNE', 2),
--     (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2026, 205.25, '2015-07-21', false, 'stripe_id_567', 'JULY', 2),
--
--
-- (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2024, 115.00, '2024-09-01', true, 'stripe_id_345', 'SEPTEMBER', 3),
-- (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2024, 115.00, '2024-09-02', true, 'stripe_id_345', 'SEPTEMBER', 3),
-- (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2024, 115.00, '2024-09-03', true, 'stripe_id_345', 'SEPTEMBER', 3),
-- (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2027, 95.80,'2028-09-01', true, 'stripe_id_890', 'AUGUST', 3),
-- (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2022, 130.50, '2022-03-03', true, 'stripe_id_123', 'MARCH', 3),
-- (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2023, 170.75, '2023-04-07', true, 'stripe_id_456', 'APRIL', 3),
-- (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2024, 95.00, '2024-05-12', false, 'stripe_id_789', 'MAY', 3),
-- (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2025, 135.00, '2015-06-17', true, 'stripe_id_234', 'JUNE', 3),
-- (CURRENT_TIMESTAMP, 1,1, false, CURRENT_TIMESTAMP,2026, 215.25, '2016-07-22', false, 'stripe_id_567', 'JULY', 3),

-- -- Insert sample data into payments table
-- INSERT INTO payments (created_date, created_by, last_modified_by, is_deleted, last_modified, year, amount, payment_date, is_paid, company_stripe_id, month, company_id)
-- VALUES
--     -- Company 1
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2022, 100.50, '2022-03-01', true, 'stripe_id_123', 'MARCH', 1),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2023, 150.75, '2023-04-05', true, 'stripe_id_456', 'APRIL', 1),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2024, 80.00, '2024-05-10', false, 'stripe_id_789', 'MAY', 1),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2025, 120.00, '2016-06-15', true, 'stripe_id_234', 'JUNE', 1),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2026, 200.25, '2016-07-20', false, 'stripe_id_567', 'JULY', 1),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2027, 90.80, '2020-08-25', true, 'stripe_id_890', 'AUGUST', 1),
--     -- Company 2
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2028, 110.00, '2028-09-30', true,'truripe_id_345', 'SEPTEMBER', 3),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2022, 110.50, '2022-03-02', true, 'stripe_id_123', 'MARCH', 2),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2023, 160.75, '2023-04-06', true, 'stripe_id_456', 'APRIL', 2),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2024, 85.00, '2024-05-11', false, 'stripe_id_789', 'MAY', 2),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2025, 125.00, '2016-06-16', true, 'stripe_id_234', 'JUNE', 2),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2026, 205.25, '2015-07-21', false, 'stripe_id_567', 'JULY', 2),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2024, 115.00, '2024-09-01', true, 'stripe_id_345', 'SEPTEMBER', 3),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2024, 115.00, '2024-09-02', true, 'stripe_id_345', 'SEPTEMBER', 3),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2024, 115.00, '2024-09-03', true, 'stripe_id_345', 'SEPTEMBER', 3),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2027, 95.80, '2028-09-01', true, 'stripe_id_890', 'AUGUST', 3),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2022, 130.50, '2022-03-03', true, 'stripe_id_123', 'MARCH', 3),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2023, 170.75, '2023-04-07', true, 'stripe_id_456', 'APRIL', 3),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2024, 95.00, '2024-05-12', false, 'stripe_id_789', 'MAY', 3),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2025, 135.00, '2015-06-17', true, 'stripe_id_234', 'JUNE', 3),
--     (CURRENT_TIMESTAMP, 1, 1, false, CURRENT_TIMESTAMP, 2026, 215.25, '2016-07-22', false, 'stripe_id_567', 'JULY', 3);
