drop table if exists brands;
create table brands (
    uuid UUID primary key,
    name varchar unique not null
);

drop table if exists categories;
create table categories (
    uuid UUID primary key,
    name varchar unique not null
);
