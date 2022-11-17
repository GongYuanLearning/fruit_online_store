use fruits;

DELETE FROM cart_item;
DELETE FROM user_delivery_address;
DELETE FROM user;
DELETE FROM fruit_order_detail;
DELETE FROM fruit_order;
DELETE FROM product;
DELETE FROM product_category;

INSERT INTO user(username, pwdHash, email, phone, birth_date) values
    ('admin', 'dkfjkdjfkdjf', 'admin@test.com', '13333333334', '2000-01-01'),
    ('user', 'dkfjkdjfkdjf', 'user@test.com', '13333333335', '1999-01-01'),
    ('test1', 'dkfjkdjfkdjf', 'test1@test.com', '12333333335', '2000-01-01'),
    ('test2', 'dkfjkdjfkdjf', 'test2@test.com', '14333333335', '1999-01-01'),
    ('test3', 'dkfjkdjfkdjf', 'test3@test.com', '15333333335', '2000-01-01'),
    ('test4', 'dkfjkdjfkdjf', 'test4@test.com', '16333333335', '1999-01-01'),
    ('test5', 'dkfjkdjfkdjf', 'test5@test.com', '17333333335', '2000-01-01');