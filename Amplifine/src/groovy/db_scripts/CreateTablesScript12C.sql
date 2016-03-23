create table GOODSTYPES
(
  id int generated always as identity (start with 100 increment by 1) primary key,
  name varchar2(75) not null
);

create table MANUFACTURERS
(
  id int generated always as identity (start with 100 increment by 1) primary key,
  name varchar2(75) not null,
  country varchar2(75),
  parent_manufacturer references MANUFACTURERS(id)
);

create table GOODS
(
  id int generated always as identity (start with 100 increment by 1) primary key,
  name varchar2(75) not null,
  manufacturer int references MANUFACTURERS(id) not null,
  goods_type int references GOODSTYPES(id) not null,
  retail_price float
);

create table SHOPS
(
  id int generated always as identity (start with 100 increment by 1) primary key,
  name varchar2(75) not null,
  shop_type int references GOODSTYPES(id),
  address varchar2(75),
  tel varchar2(75)
);

create table UNITSUPPLIES
(
  id int generated always as identity (start with 100 increment by 1) primary key,
  id_shop int references SHOPS(id) not null,
  id_good int references GOODS(id) not null,
  purchasing_price float not null,
  amount int
);

create table SUPPLIERS
(
  id int generated always as identity (start with 100 increment by 1) primary key,
  name varchar2(75) not null
);

create table SUPPLIES
(
  id int generated always as identity (start with 100 increment by 1) primary key,
  id_supplie int not null,
  id_unit_supplie int references UNITSUPPLIES(id) not null,
  id_supplier int references SUPPLIERS(id) not null,
  date_supplie date not null
);

create table UNITSALES
(
  id int generated always as identity (start with 100 increment by 1) primary key,
  id_good int references GOODS(id) not null,
  amount int,
  full_retail_price float
);

create table WORKERS
(
  id int generated always as identity (start with 100 increment by 1) primary key,
  surname varchar2(50) not null,
  name varchar2(50) not null,
  birthday date not null,
  fired date default null,
  enter date not null
);

create table WORKINGDAYS
(
  id int generated always as identity (start with 100 increment by 1) primary key,
  id_worker int references WORKERS(id) not null,
  id_shop int references SHOPS(id) not null,
  working_date date not null,
  time_start timestamp not null,
  time_finish timestamp not null
);

create table SALES
(
  id int generated always as identity (start with 100 increment by 1) primary key,
  id_sale int not null,
  id_worker int references WORKERS(id) not null,
  id_shop int references SHOPS(id) not null,
  id_unitsale int references UNITSALES(id) not null,
  time_sale timestamp not null
);
