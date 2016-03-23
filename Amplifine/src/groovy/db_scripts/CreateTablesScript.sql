create table GOODSTYPES
(
  id int primary key,
  name varchar2(75) not null
);

create sequence goodstypes_id start with 100 increment by 1;

create or replace trigger trg_goodstypes_id before insert on GOODSTYPES for each row
begin
  select goodstypes_id.nextval
  into :new.id
  from dual;
end;

create table MANUFACTURERS
(
  id int primary key,
  name varchar2(75) not null,
  country varchar2(75),
  parent_manufacturer int,
  constraint fk_parent_manufacturer foreign key (parent_manufacturer) references MANUFACTURERS(id)
);

create sequence manufacturers_id start with 100 increment by 1;

create or replace trigger trg_manufacturers_id before insert on MANUFACTURERS for each row
begin
  select manufacturers_id.nextval
  into :new.id
  from dual;
end;

create table GOODS
(
  id int primary key,
  name varchar2(75) not null,
  manufacturer int not null,
  goods_type int not null,
  amount int not null,
  retail_price float not null,
  constraint fk_good_manufacturer foreign key (manufacturer) references MANUFACTURERS(id),
  constraint fk_good_type foreign key (goods_type) references GOODSTYPES(id)
);

create sequence goods_id start with 100 increment by 1;

create or replace trigger trg_goods_id before insert on GOODS for each row
begin
  select goods_id.nextval
  into :new.id
  from dual;
end;

create table SHOPS
(
  id int primary key,
  name varchar2(75) not null,
  shop_type int,
  address varchar2(75) not null,
  tel varchar2(75),
  constraint fk_shop_type foreign key (shop_type) references GOODSTYPES(id)
);

create sequence shops_id start with 100 increment by 1;

create or replace trigger trg_shops_id before insert on SHOPS for each row
begin
  select shops_id.nextval
  into :new.id
  from dual;
end;

create table SUPPLIERS
(
  id int primary key,
  name varchar2(75) not null
);

create sequence suppliers_id start with 100 increment by 1;

create or replace trigger trg_suppliers_id before insert on SUPPLIERS for each row
begin
  select suppliers_id.nextval
  into :new.id
  from dual;
end;

create table SUPPLIES
(
  id int primary key,
  id_supplier int not null,
  id_shop int not null,
  date_supply date not null,
  constraint fk_supplies_shop foreign key (id_shop) references SHOPS(id),
  constraint fk_supplies_supplier foreign key (id_supplier) references SUPPLIERS(id)
);

create sequence supplies_id start with 100 increment by 1;

create or replace trigger trg_supplies_id before insert on SUPPLIES for each row
begin
  select supplies_id.nextval
  into :new.id
  from dual;
end;

create table UNITSUPPLIES
(
  id int primary key,
  id_supply int not null,
  id_good int not null,
  amount int not null,
  purchasing_price float not null,
  constraint fk_unitsupplies_supply foreign key (id_supply) references SUPPLIES(id),
  constraint fk_unitsupplies_good foreign key (id_good) references GOODS(id),
  constraint unique_good_supply unique (id_good, id_supply)
);

create sequence unitsupplies_id start with 100 increment by 1;

create or replace trigger trg_unitsupplies_id before insert on UNITSUPPLIES for each row
begin
  select unitsupplies_id.nextval
  into :new.id
  from dual;
end;

create table WORKERS
(
  id int primary key,
  surname varchar2(50) not null,
  name varchar2(50) not null,
  birthday date not null,
  fired date default null,
  enter date not null
);

create sequence workers_id start with 100 increment by 1;

create or replace trigger trg_workers_id before insert on WORKERS for each row
begin
  select workers_id.nextval
  into :new.id
  from dual;
end;

create table WORKINGDAYS
(
  id int primary key,
  id_worker int not null,
  id_shop int not null,
  working_date date not null,
  time_start int not null,
  time_finish int not null,
  constraint fk_workingdays_worker foreign key (id_worker) references WORKERS(id),
  constraint fk_workingdays_shop foreign key (id_shop) references SHOPS(id),
  constraint unique_worker_day unique (id_worker, working_date)
);

create sequence workingdays_id start with 100 increment by 1;

create or replace trigger trg_workingdays_id before insert on WORKINGDAYS for each row
begin
  select workingdays_id.nextval
  into :new.id
  from dual;
end;

create table SALES
(
  id int primary key,
  id_worker int not null,
  sale_time timestamp not null,
  constraint fk_sales_worker foreign key (id_worker) references WORKERS(id)
);

create sequence sales_id start with 100 increment by 1;

create or replace trigger trg_sales_id before insert on SALES for each row
begin
  select sales_id.nextval
  into :new.id
  from dual;
end;

create table UNITSALES
(
  id int primary key,
  id_good int not null,
  id_sale int not null,
  amount int not null,
  full_retail_price float,
  constraint fk_unitsales_good foreign key (id_good) references GOODS(id),
  constraint fk_unitsales_sale foreign key (id_sale) references SALES(id),
  constraint unique_good_sale unique (id_good, id_sale)
);

create sequence unitsales_id start with 100 increment by 1;

create or replace trigger trg_unitsales_id before insert on UNITSALES for each row
begin
  select unitsales_id.nextval
  into :new.id
  from dual;
end;
