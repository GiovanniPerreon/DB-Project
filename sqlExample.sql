ALTER USER 'root'@'localhost' IDENTIFIED BY 'password';
FLUSH PRIVILEGES;
-- Database Deletion -------------------------------------------------------------
DROP database if exists sqlTest;

-- Tables creation -------------------------------------------------------------

create database if not exists sqlTest;
use sqlTest;

create table if not exists TAG (
    name varchar(32) not null,
    constraint tag_pk primary key (name)
);

create table if not exists MATERIAL (
    code int not null auto_increment,
    description varchar(64) not null,
    constraint material_pk primary key (code)
);

create table if not exists PRODUCT (
    code int not null auto_increment,
    name varchar(64) not null,
    description varchar(5000),
    constraint product_pk primary key (code)
);

create table if not exists COMPOSITION (
    product_code int not null,
    material_code int not null,
    percent float(1) not null,
    constraint composition_pk primary key (product_code, material_code)
);

create table if not exists TAGGED (
    product_code int not null,
    tag_name varchar(32) not null,
    constraint tagged_pk primary key (tag_name, product_code)
);

-- Constraints -----------------------------------------------------------------

alter table COMPOSITION add constraint composition_references_material
foreign key (material_code)
references MATERIAL (code);

alter table COMPOSITION add constraint composition_references_product
foreign key (product_code)
references PRODUCT (code);

alter table TAGGED add constraint tagged_references_tag
foreign key (tag_name)
references TAG (name);

alter table TAGGED add constraint tagged_references_product
foreign key (product_code)
references PRODUCT (code);

-- Inserts --------------------------------------------------------------------
insert into TAG (name) values
('eco-friendly'),
('luxury'),
('handmade'),
('summer'),
('winter');
insert into MATERIAL (description) values
('Cotton'),
('Wool'),
('Polyester'),
('Silk'),
('Linen');
insert into PRODUCT (name, description) values
('Cozy Blanket', 'Warm wool blanket, perfect for cold seasons.'),
('Summer Shirt', 'Lightweight linen shirt ideal for summer.'),
('Luxury Scarf', 'Silk scarf with elegant pattern.'),
('Eco Bag', 'Reusable cotton bag, 100% biodegradable.'),
('Winter Hat', 'Woolen hat with inner lining for extra warmth.');
insert into COMPOSITION (product_code, material_code, percent) values
(1, 2, 100),  -- Cozy Blanket: 100% Wool
(2, 5, 100),  -- Summer Shirt: 100% Linen
(3, 4, 100),  -- Luxury Scarf: 100% Silk
(4, 1, 100),  -- Eco Bag: 100% Cotton
(5, 2, 80),   -- Winter Hat: 80% Wool
(5, 3, 20);   -- Winter Hat: 20% Polyester
insert into TAGGED (product_code, tag_name) values
(1, 'winter'),
(2, 'summer'),
(3, 'luxury'),
(4, 'eco-friendly'),
(5, 'winter'),
(4, 'handmade');