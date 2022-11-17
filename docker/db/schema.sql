DROP DATABASE IF EXISTS fruits;
CREATE DATABASE IF NOT EXISTS fruits;
USE fruits;
CREATE table IF NOT EXISTS user(
                                   id bigint(20) unsigned primary key auto_increment,
                                   username varchar(255) not null unique ,
                                   pwdHash varchar(255) not null,  -- 密码的hash值
                                   email varchar(255) not null unique,
                                   phone varchar(11) unique
);
create table IF NOT EXISTS product_category (
                                                id bigint(20) unsigned primary key auto_increment,
                                                name varchar(255) not null unique
);
create table IF NOT EXISTS product (
                                       id bigint(20) unsigned primary key auto_increment,
                                       name varchar(255) not null,
                                       imagePath varchar(255) not null,
                                       description varchar(512),
                                       detail longtext not null,
                                       price decimal(10, 2),
                                       unit char(10),
                                       on_store_time datetime default current_timestamp,
                                       category_id bigint(20) unsigned,
                                       constraint fk_product_category_id foreign key (category_id) references product_category(id)
);
create table IF NOT EXISTS cart_item (
                                         id bigint(20) unsigned primary key auto_increment,
                                         product_id bigint(20) unsigned,
                                         user_id bigint(20) unsigned,
                                         count int default 0,
                                         total_amount decimal(10,2) default 0,
                                         constraint fk_product_id foreign key (product_id) references product(id),
                                         constraint fk_user_id foreign key (user_id) references user(id)
);
