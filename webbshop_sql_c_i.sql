-- create database and tables

drop database if exists webbshop;
create database webbshop;
use webbshop;

create table Shoe(
id int not null auto_increment primary key,
brand varchar(100) not null,
color varchar(100) not null,
size int not null,
price int not null,
quantity int not null
);

create table Category(
id int not null auto_increment primary key,
name varchar(100) not null
);

create table Customer(
id int not null auto_increment primary key,
name varchar(100) not null,
city varchar(100) not null,
password varchar(100) not null
);

create table CustomerOrder(
id int not null auto_increment primary key,
orderdate date not null,
customerid int not null,
foreign key (customerid) references Customer(id),
status enum("ACTIVE", "PAID") not null default 'ACTIVE'
);

create table ShoeInfo(
id int not null auto_increment primary key,
shoeid int not null,
foreign key (shoeid) references Shoe(id) on delete cascade,
categoryid int not null,
foreign key (categoryid) references Category(id)
);
-- ShoeInfo is useless without a ShoeId

create table OrderInfo(
id int not null auto_increment primary key,
shoeid int not null,
foreign key (shoeid) references Shoe(id),
customerorderid int not null,
foreign key (customerorderid) references CustomerOrder(id) on delete cascade
);
-- OrderInfo is useless without a CustomerOrderId

create table OutOfStock(
id int not null auto_increment primary key,
shoeid int not null,
foreign key (shoeid) references Shoe(id),
dateoutofstock timestamp not null
);


-- insert data into tables
insert into Shoe(brand, color, size, price, quantity) values
('Ecco', 'Black', '38', '500', '30'),
('Nike', 'White', '42', '750', '20'),
('Adidas', 'Blue', '41', '700', '25'),
('Puma', 'Red', '40', '650', '15'),
('Timberland', 'Brown', '43', '1200', '10'),
('Salomon', 'Green', '44', '1100', '12'),
('Vans', 'Black', '39', '550', '18'),
('Converse', 'White', '37', '500', '22'),
('Reebok', 'Grey', '42', '680', '16'),
('Merrell', 'Black', '45', '1300', '1');

insert into Category(name) values
('Mens'),
('Womens'),
('Kids'),
('Sport'),
('Outdoor'),
('Waterproof'),
('Sandals');

insert into Customer(name, city, password) values
('Melvin', 'Stockholm', 'pass1'),
('Tim', 'Stockholm', 'pass2'),
('Emil', 'Stockholm', 'pass3'),
('Sigrun', 'Uppsala', 'pass4'),
('David', 'Helsingborg', 'pass5');

insert into CustomerOrder(orderdate, customerid, status) values
('2026-01-22', 1, 'PAID'),
('2026-01-23', 2, 'PAID'),
('2026-02-08', 3, 'PAID'),
('2026-02-24', 4, 'PAID'),
('2026-04-01', 5, 'PAID'),
('2026-04-25', 1, 'ACTIVE'),
('2026-05-22', 2, 'ACTIVE'),
('2026-07-02', 3, 'ACTIVE'),
('2026-07-03', 4, 'ACTIVE');

insert into ShoeInfo(shoeid, categoryid) values
(1, 2),
(1, 5),
(1, 7),
(2, 1),
(2, 4),
(3, 1),
(3, 4),
(4, 2),
(4, 4),
(5, 1),
(5, 5),
(6, 5),
(6, 6),
(7, 2),
(8, 3),
(9, 1),
(9, 4),
(10, 5),
(10, 6);

insert into OrderInfo(shoeid, customerorderid) values
(1, 1),
(2, 1),

(3, 2),
(4, 2),
(5, 2),

(6, 3),
(1, 3),

(7, 4),
(8, 4),
(2, 4),

(9, 5),

(10, 6),
(6, 6),
(3, 6),

(4, 7),
(7, 7),

(8, 8),
(1, 8),
(9, 8),

(2, 9),
(10, 9);

-- Index for the customers name, it is something that is being used often in queries
create index ix_name on customer(name);