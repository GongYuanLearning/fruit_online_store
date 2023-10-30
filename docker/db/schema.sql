DROP DATABASE IF EXISTS fruits;

CREATE DATABASE fruits DEFAULT CHARACTER
SET
    utf8mb4 COLLATE utf8mb4_0900_ai_ci;

USE fruits;

DROP TABLE IF EXISTS cart_item;

DROP TABLE IF EXISTS user_delivery_address;

DROP TABLE IF EXISTS user;

DROP TABLE IF EXISTS fruit_order_detail;

DROP TABLE IF EXISTS fruit_order;

DROP TABLE IF EXISTS product;

DROP TABLE IF EXISTS product_category;

CREATE TABLE IF NOT EXISTS user(
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE CHECK(username REGEXP '\\w{3,}'),
    pwdHash VARCHAR(255) NOT NULL, -- 密码的hash值
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(11) UNIQUE CHECK(phone REGEXP '1[0-9]{10}'),
    birth_date DATETIME(6)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS product_category (
                                                id BIGINT UNSIGNED PRIMARY KEY auto_increment,
                                                name VARCHAR(255) NOT NULL UNIQUE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS product (
                                       id BIGINT UNSIGNED PRIMARY KEY auto_increment,
                                       name VARCHAR(255) NOT NULL,
                                       imagePath VARCHAR(255) NOT NULL,
                                       description VARCHAR(512),
                                       detail LONGTEXT NOT NULL,
                                       price DECIMAL(10, 2),
                                       unit CHAR(10),
                                       on_store_time datetime DEFAULT CURRENT_TIMESTAMP,
                                       category_id BIGINT UNSIGNED,
                                       CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES product_category(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS cart_item (
                                         id BIGINT UNSIGNED PRIMARY KEY auto_increment,
                                         product_id BIGINT UNSIGNED,
                                         user_id BIGINT UNSIGNED,
                                         count INT DEFAULT 0,
                                         total_amount DECIMAL(10, 2) DEFAULT 0,
                                         CONSTRAINT fk_cart_item_product_id FOREIGN KEY (product_id) REFERENCES product(id),
                                         CONSTRAINT fk_cart_item_user_id FOREIGN KEY (user_id) REFERENCES user(id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS user_delivery_address (
                                                     id BIGINT UNSIGNED PRIMARY KEY auto_increment,
                                                     user_id BIGINT UNSIGNED,
                                                     receiver_name VARCHAR(50) NOT NULL,
                                                     address_detail VARCHAR(255) NOT NULL,
                                                     post_code CHAR(6) CHECK(post_code REGEXP '[0-9]{6}'),
     phone VARCHAR(11) UNIQUE CHECK(phone REGEXP '1[0-9]{10}'),
     CONSTRAINT fk_user_delivery_address_user_id FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS fruit_order(
                                          id BIGINT UNSIGNED PRIMARY KEY auto_increment,
                                          order_no CHAR(8) NOT NULL UNIQUE,
                                          created_by BIGINT UNSIGNED NOT NULL,
                                          created_date datetime(6) NOT NULL,
                                          updated_by BIGINT UNSIGNED NOT NULL,
                                          updated_date datetime(6) NOT NULL,
                                          status TINYINT NOT NULL DEFAULT 1,
                                          total_amount DECIMAL(10, 2) DEFAULT 0,
                                          CONSTRAINT fk_fruit_order_created_by FOREIGN KEY(created_by) REFERENCES user(id),
                                          CONSTRAINT fk_fruit_order_updated_by FOREIGN KEY(updated_by) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS fruit_order_detail (
                                                  id BIGINT UNSIGNED PRIMARY KEY auto_increment,
                                                  order_id BIGINT UNSIGNED NOT NULL,
                                                  product_id BIGINT UNSIGNED NOT NULL,
                                                  count INT DEFAULT 0,
                                                  CONSTRAINT fk_fruit_order_detail_product_id FOREIGN KEY (product_id) REFERENCES product(id),
                                                  CONSTRAINT fk_fruit_order_detail_order_id FOREIGN KEY (order_id) REFERENCES fruit_order(id)
);