insert into category (name)
VALUES ('Marshmallow');
insert into category (name)
VALUES ('Chocolate');
insert into category (name)
VALUES ('Cake');

insert into category (name, parent_category_id)
VALUES ('Chocolate Dipped Marshmallows', (SELECT category_id FROM category WHERE name = 'Marshmallow')),
       ('Apple Marshmallows', (SELECT category_id FROM category WHERE name = 'Marshmallow')),
       ('Dark Chocolate', (SELECT category_id FROM category WHERE name = 'Chocolate')),
       ('Milk Chocolate', (SELECT category_id FROM category WHERE name = 'Chocolate')),
       ('White Chocolate', (SELECT category_id FROM category WHERE name = 'Chocolate')),
       ('Chocolate Cake', (SELECT category_id FROM category WHERE name = 'Cake')),
       ('Low Fat Cake', (SELECT category_id FROM category WHERE name = 'Cake'));


insert into product(name, photo_path, price, description, category_id, is_available)
values ('Milka', '/storage/milka.jpg', 30.05, 'The best chocolate in your life', (SELECT category_id FROM category WHERE name = 'Milk Chocolate'), true),
       ('White Milka', '/storage/milka.jpg', 33.05, 'The best chocolate in your life', (SELECT category_id FROM category WHERE name = 'White Chocolate'), true),
       ('Millennium', '/storage/milka.jpg', 38.09, 'The best chocolate in your life', (SELECT category_id FROM category WHERE name = 'Dark Chocolate'), true),
       ('Paradise', '/storage/milka.jpg', 40.05, 'Paradise on Earth', (SELECT category_id FROM category WHERE name = 'Chocolate Dipped Marshmallows'), true),
       ('Apple Boom', '/storage/milka.jpg', 30.00, 'We are better than Apple', (SELECT category_id FROM category WHERE name = 'Apple Marshmallows'), true),
       ('Fat bear', '/storage/fat_bear.jpg', 130.05, 'You only live once', (SELECT category_id FROM category WHERE name = 'Chocolate Cake'), false),
       ('Fat and Sweet', '/storage/chocolate_cake.jpg', 320.05, 'You only live once', (SELECT category_id FROM category WHERE name = 'Chocolate Cake'), false),
       ('Fat and Happy', '/storage/fat_sweet.jpg', 390.05, 'You only live once', (SELECT category_id FROM category WHERE name = 'Chocolate Cake'), true),
       ('Cherry and Happiness', '/storage/cherry_hapiness.jpg', 530.05, 'Dont worry, be happy', (SELECT category_id FROM category WHERE name = 'Low Fat Cake'), false),
       ('Banana and Happiness', '/storage/banana_happiness.jpg', 430.05, 'Dont worry, be happy', (SELECT category_id FROM category WHERE name = 'Low Fat Cake'), true);

insert into role(name)
values ('ROLE_ADMIN'),
       ('ROLE_USER');

insert into user(password, username)
values ('$2a$10$RYofohWktwl5xDkPm9FQQuwjubusMDz9rfO21tUWb1dB.41HRBq3C', 'admin'),
       ('$2a$10$NOfPE/3LRpK/9GkOxn7ls.0GGdqC37GZOxoaFZSZkcNvpzEaxfbTG', 'testuser');

insert into user_roles
values ((select user_id from user where username = 'admin'), (select role_id from role where name = 'ROLE_ADMIN')),
       ((select user_id from user where username = 'testuser'), (select role_id from role where name = 'ROLE_USER'));

insert into sweet_order (created_date, user_id)
    values(sysdate, select user_id from user where username = 'testuser'),
          (sysdate, select user_id from user where username = 'testuser');

insert into ORDER_ITEM(product_id, sweet_order_id)
values (select PRODUCT_ID from PRODUCT where NAME = 'Milka',
        select max(sweet_order_id) from sweet_order where user_id in (select user_id from user where username = 'testuser')),
       (select PRODUCT_ID from PRODUCT where NAME = 'Milka',
        select max(sweet_order_id) from sweet_order where user_id in (select user_id from user where username = 'testuser')),
       (select PRODUCT_ID from PRODUCT where NAME = 'Milka',
        select max(sweet_order_id) from sweet_order where user_id in (select user_id from user where username = 'testuser')),
       (select PRODUCT_ID from PRODUCT where NAME = 'Fat bear',
        select max(sweet_order_id) from sweet_order where user_id in (select user_id from user where username = 'testuser')),
        (select PRODUCT_ID from PRODUCT where NAME = 'White Milka',
        select min(sweet_order_id) from sweet_order where user_id in (select user_id from user where username = 'testuser')),
       (select PRODUCT_ID from PRODUCT where NAME = 'Banana and Happiness',
        select min(sweet_order_id) from sweet_order where user_id in (select user_id from user where username = 'testuser')),
       (select PRODUCT_ID from PRODUCT where NAME = 'Fat and Happy',
        select min(sweet_order_id) from sweet_order where user_id in (select user_id from user where username = 'testuser')),
       (select PRODUCT_ID from PRODUCT where NAME = 'Paradise',
        select min(sweet_order_id) from sweet_order where user_id in (select user_id from user where username = 'testuser'));