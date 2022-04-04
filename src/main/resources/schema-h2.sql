create table goods (goods_id bigint not null auto_increment, created_date date, modified_date date,
goods_price integer, goods_publisher varchar(50), goods_status varchar(50), goods_title varchar(100),
goods_writer varchar(50), GOODS_ISBN VARCHAR(50), GOODS_SALES_PRICE integer, GOODS_PUBLISHED_DATE date,
primary key (goods_id)) engine=InnoDB;

create table image_file (image_id bigint not null auto_increment, created_date date,
modified_date date, file_name varchar(50), file_type varchar(40), reg_id varchar(20),
goods_id bigint not null, primary key (image_id),
FOREIGN KEY (goods_id) REFERENCES goods (goods_id)) engine=InnoDB;

create table user (id bigint not null auto_increment, created_date datetime,
modified_date datetime, email varchar(255) not null, name varchar(255) not null,
picture varchar(255), role varchar(255) not null, primary key (id)) engine=InnoDB;


insert into goods (goods_title, goods_writer, goods_publisher, goods_price, goods_status, GOODS_ISBN,
GOODS_SALES_PRICE, GOODS_PUBLISHED_DATE,
created_date, modified_date) values ('무작정 따라가기 홍콩 마카오','김수정, 김승남','인포북스',30000, 'bestseller',
'121212', 27000, '2018-10-16', '2018-10-16', now());

insert into goods (goods_title, goods_writer, goods_publisher, goods_price, goods_status, GOODS_ISBN,
GOODS_SALES_PRICE, GOODS_PUBLISHED_DATE,
created_date, modified_date) values ('모두의 파이썬','이승찬','길벗',12000, 'bestseller',
'9791186978894', 10800, '2018-10-17', '2018-10-17', now());

insert into goods (goods_title, goods_writer, goods_publisher, goods_price, goods_status, GOODS_ISBN,
GOODS_SALES_PRICE, GOODS_PUBLISHED_DATE,
created_date, modified_date) values ('리액트를 다루는 기술','김민준','길벗',32000, 'bestseller',
'9791160505238', 32000, '2018-10-17', '2018-10-17', now());


insert into image_file (GOODS_ID,file_name,REG_ID,file_type,created_date, modified_date) values
((SELECT GOODS_ID from goods WHERE GOODS_ISBN='121212'), '무작정 따라가기 홍콩 마카오.jpg',
'admin','main_image', '2018-10-16', now());

Insert into image_file (GOODS_ID,file_name,REG_ID,file_type,created_date, modified_date) values
((SELECT GOODS_ID from goods WHERE GOODS_ISBN='121212'),'무작정 따라가기 홍콩 마카오_상세.jpg',
'admin','detail_image1','2018-10-16', now());
Insert into image_file (GOODS_ID,file_name,REG_ID,file_type,created_date, modified_date) values
((SELECT GOODS_ID from goods WHERE GOODS_ISBN='121212'),'detail2.jpg',
'admin','detail_image2','2018-10-16', now());


insert into image_file (GOODS_ID,file_name,REG_ID,file_type,created_date, modified_date) values
((SELECT GOODS_ID from goods WHERE GOODS_ISBN='9791186978894'), 'image2.jpg',
'admin','main_image', '2018-10-17', now());

Insert into image_file (GOODS_ID,file_name,REG_ID,file_type,created_date, modified_date) values
((SELECT GOODS_ID from goods WHERE GOODS_ISBN='9791186978894'),'모두의 파이선상세이미지1.jpg',
'admin','detail_image1', '2018-10-17', now());

insert into image_file (GOODS_ID,file_name,REG_ID,file_type,created_date, modified_date) values
((SELECT GOODS_ID from goods WHERE GOODS_ISBN='9791160505238'), 'main_react.jpg',
'admin','main_image', '2018-10-17', now());

Insert into image_file (GOODS_ID,file_name,REG_ID,file_type,created_date, modified_date) values
((SELECT GOODS_ID from goods WHERE GOODS_ISBN='9791160505238'),'react_detail1.jpg',
'admin','detail_image1','2018-10-17', now());

