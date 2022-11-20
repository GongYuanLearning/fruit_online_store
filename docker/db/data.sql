use fruits;

DELETE FROM cart_item;
DELETE FROM user_delivery_address;
DELETE FROM user;
DELETE FROM fruit_order_detail;
DELETE FROM fruit_order;
DELETE FROM product;
DELETE FROM product_category;

-- password is 'password'
INSERT INTO user(username, pwdHash, email, phone, birth_date) values
                                                                  ('admin', '{SMD5}taH4jBjvTIymiYh+ISoAG3cQ8GqByCkv', 'admin@test.com', '13333333334', '2000-01-01'),
                                                                  ('user', '{SMD5}taH4jBjvTIymiYh+ISoAG3cQ8GqByCkv', 'user@test.com', '13333333335', '1999-01-01'),
                                                                  ('test1', '{SMD5}taH4jBjvTIymiYh+ISoAG3cQ8GqByCkv', 'test1@test.com', '12333333335', '2000-01-01'),
                                                                  ('test2', '{SMD5}taH4jBjvTIymiYh+ISoAG3cQ8GqByCkv', 'test2@test.com', '14333333335', '1999-01-01'),
                                                                  ('test3', '{SMD5}taH4jBjvTIymiYh+ISoAG3cQ8GqByCkv', 'test3@test.com', '15333333335', '2000-01-01'),
                                                                  ('test4', '{SMD5}taH4jBjvTIymiYh+ISoAG3cQ8GqByCkv', 'test4@test.com', '16333333335', '1999-01-01'),
                                                                  ('test5', '{SMD5}taH4jBjvTIymiYh+ISoAG3cQ8GqByCkv', 'test5@test.com', '17333333335', '2000-01-01');

INSERT INTO product_category(id, name) VALUES
                                           (1, '新鲜水果'),
                                           (2, '海鲜水产)');

INSERT INTO product(name, imagePath, description, detail, price, unit, on_store_time, category_id) VALUES
                                                                                                       ('草莓', 'images/goods/goods001.jpg', '长沙本地草莓', '长沙本地草莓', 50.00, '500g', current_timestamp(), 1),
                                                                                                       ('青芒', 'images/goods/goods002.jpg', '海南青芒', '海南青芒', 80.00, '500g', current_timestamp(), 1);