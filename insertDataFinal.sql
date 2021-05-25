insert into users 
values (100, 'guess fname', 'guess lname', 'guess@guess.com', 'guess', 'guess');
insert into users 
values (101, 'guess fname', 'guess lname', 'guess2@guess.com', 'guess', 'guess');
insert into users 
values (102, 'guess fname', 'guess lname', 'guess3@guess.com', 'guess', 'guess');

insert into users
values (103, 'seller fname', 'seller lname', 'seller@seller.com', 'seller', 'seller');
insert into users
values (104, 'seller fname', 'seller lname', 'seller2@seller.com', 'seller', 'seller');
insert into users
values (105, 'seller fname', 'seller lname', 'seller3@seller.com', 'seller', 'seller');

insert into users_roles
select R.role_id, U.id
from roles R, users U
where R.name = 'ROLE_USER' and U.id = 100;
insert into users_roles
select R.role_id, U.id
from roles R, users U
where R.name = 'ROLE_USER' and U.id = 101;
insert into users_roles
select R.role_id, U.id
from roles R, users U
where R.name = 'ROLE_USER' and U.id = 102;


insert into users_roles
select R.role_id, U.id
from roles R, users U
where R.name = 'ROLE_SELLER' and U.id = 103;
insert into users_roles
select R.role_id, U.id
from roles R, users U
where R.name = 'ROLE_SELLER' and U.id = 104;
insert into users_roles
select R.role_id, U.id
from roles R, users U
where R.name = 'ROLE_SELLER' and U.id = 105;

insert into stores
values (106, 'owner seller@seller.com', 103);
insert into stores
values (107, 'shop name', 104);

insert into product
values (108, 'Banana', 'A ripe banana, perfect to use for scale', '15000', 16, 106, 'banana-product.png');
insert into product
values (109, 'Toilet paper', 'Rare item once, legend saids one third of the rolls are triple ply', '15000', 20, 106, 'paper-product.png');
insert into product
values (110, 'Potato', 'Sorry for the long post', '20000', 7, 106, 'potato-product.png');
insert into product
values (111, 'Iphone 11XS', 'As cheap as a potato', '20000', 10, 107, 'phone-product.png');

insert into orders
values (112, 100, 108, 7, 106, '7 Sasimi Str', 'Tue May 25 2021');
insert into orders
values (113, 100, 109, 5, 106, '7 Sasimi Str', 'Tue May 25 2021');
insert into orders
values (114, 100, 110, 3, 106, '7 Sasimi Str', 'Tue May 25 2021');
insert into orders
values (115, 102, 108, 5, 106, '7 Sasimi Str', 'Wed May 26 2021');
insert into orders
values (116, 100, 108, 7, 106, '7 Sasimi Str', 'Thu May 27 2021');
