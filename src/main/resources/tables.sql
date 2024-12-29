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

drop table if exists items;
create table items (
    uuid UUID primary key,
    name varchar unique not null,
    description varchar not null,
    price numeric not null,
    brand_id UUID not null,
    category_id UUID not null,
    constraint brand_id_fkey foreign key (brand_id)
        references brands (uuid) match simple
        on update no action on delete no action,
    constraint cat_id_fkey foreign key (category_id)
        references categories (uuid) match simple
        on update no action on delete no action
);
