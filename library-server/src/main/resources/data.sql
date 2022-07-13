INSERT INTO public.librarians (id, created_at, updated_at, city, state, street, zip_code, first_name, last_name, telephone, appointed_date) VALUES (1, '2022-07-08 19:57:57.000000', '2022-07-08 19:57:57.000000', 'Fairfield', 'IOWA', '1000 North 4th Street', 52557, 'Ben', 'Aflick', '6214567896', null);

INSERT INTO public.customers (id, email, customer_number, created_at, updated_at, city, state, street, zip_code, first_name, last_name, telephone) VALUES (3, 'julia@mail.com','EA003','2022-07-08 19:59:37.000000', '2022-07-08 19:59:37.000000', 'Fairfield', 'IOWA', '1000N 4th Street', 52557, 'Julia', 'Roberts', '6321789555');
INSERT INTO public.customers (id, email, customer_number, created_at, updated_at, city, state, street, zip_code, first_name, last_name, telephone) VALUES (2, 'eva@mail.com','EA002','2022-07-08 19:59:37.000000', '2022-07-08 19:59:37.000000', 'Fairfield', 'IOWA', '1000N 4th Street', 52557, 'Eva', 'Mendes', '6321789555');
INSERT INTO public.customers (id, email, customer_number,  created_at, updated_at, city, state, street, zip_code, first_name, last_name, telephone) VALUES (1,'scarlet@mail.com','EA001', '2022-07-08 19:59:37.000000', '2022-07-08 19:59:37.000000', 'Fairfield', 'IOWA', '1000N 4th Street', 52557, 'Scarlet', 'Johanson', '6321789555');

INSERT INTO public.reservation_entry (id, created_at, updated_at, active, isbn, customer_id) VALUES (1, '2022-07-08 20:18:43.000000', '2022-07-08 20:18:43.000000', true,  '23-11451', 2);
INSERT INTO public.reservation_entry (id, created_at, updated_at, active, isbn, customer_id) VALUES (2, '2022-07-08 20:18:43.000000', '2022-07-08 20:18:43.000000', true,  '28-12331', 2);
INSERT INTO public.reservation_entry (id, created_at, updated_at, active, isbn, customer_id) VALUES (3, '2022-07-08 20:18:43.000000', '2022-07-08 20:18:43.000000', true,  '48-56882', 2);
INSERT INTO public.reservation_entry (id, created_at, updated_at, active, isbn, customer_id) VALUES (4, '2022-07-08 20:18:43.000000', '2022-07-08 20:18:43.000000', true,  '11-11111', 1);
INSERT INTO public.reservation_entry (id, created_at, updated_at, active, isbn, customer_id) VALUES (5, '2022-07-08 20:18:43.000000', '2022-07-08 20:18:43.000000', false, '99-22223', 1);

INSERT INTO checkout_entry (id, created_at, updated_at, borrowed_date, due_date, is_returned, isbn, return_date, scan_code, customer_id)
VALUES (1, '2022-06-08 20:23:32.000000', '2022-06-08 20:23:32.000000', '2022-06-08 00:00:00.000000', '2022-06-29 00:00:00.000000', true, '23-11451', '2022-07-01 00:00:00.000000', 'A00100BOOK1', 1);
INSERT INTO checkout_entry (id, created_at, updated_at, borrowed_date, due_date, is_returned, isbn, return_date, scan_code, customer_id)
VALUES (2, now() - interval '30 day', now() - interval '30 day', now() - interval '30 day', now() - interval '9 day', false, '28-12331', null, 'A00100BOOK2', 1);
INSERT INTO checkout_entry (id, created_at, updated_at, borrowed_date, due_date, is_returned, isbn, return_date, scan_code, customer_id)
VALUES (3, now() - interval '30 day', now() - interval '30 day', now() - interval '30 day', now() - interval '9 day', false, '28-12331', null, 'A00100BOOL5', 1);
INSERT INTO checkout_entry (id, created_at, updated_at, borrowed_date, due_date, is_returned, isbn, return_date, scan_code, customer_id)
VALUES (4, now() - interval '20 day', now() - interval '20 day', now() - interval '20 day', now() + interval '1 day', false , '28-12331', null, 'A00100BOOL3', 1);
INSERT INTO checkout_entry (id, created_at, updated_at, borrowed_date, due_date, is_returned, isbn, return_date, scan_code, customer_id)
VALUES (5, now() - interval '20 day', now() - interval '20 day', now() - interval '20 day', now() + interval '1 day', false , '28-12331', null, 'A00100BOOL4', 1);
INSERT INTO checkout_entry (id, created_at, updated_at, borrowed_date, due_date, is_returned, isbn, return_date, scan_code, customer_id)
VALUES (6, now() - interval '20 day', now() - interval '20 day', now() - interval '20 day', now() + interval '1 day', false , '48-56882', null, 'A00100BOOK4', 2);
INSERT INTO checkout_entry (id, created_at, updated_at, borrowed_date, due_date, is_returned, isbn, return_date, scan_code, customer_id)
VALUES (7, now() - interval '20 day', now() - interval '20 day', now() - interval '20 day', now() + interval '1 day', false , '11-11111', null, 'A00100BOOK5', 2);

select setval('hibernate_sequence', 100);

