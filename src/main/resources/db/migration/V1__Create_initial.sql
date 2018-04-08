create table users (
    id serial primary key,
    created_date timestamp,
    updated_date timestamp,
    name varchar(255)
);

create table flowers (
    id serial primary key,
    created_date timestamp,
    updated_date timestamp,
    name varchar(255),
    description text
);

create table user_flowers (
    id serial primary key,
    created_date timestamp,
    updated_date timestamp,
    user_id integer REFERENCES users(id),
    flower_id integer REFERENCES flowers(id)
);

create table waterings (
    id serial primary key,
    created_date timestamp,
    updated_date timestamp,
    flower_id integer REFERENCES flowers(id),
    notes text
);
