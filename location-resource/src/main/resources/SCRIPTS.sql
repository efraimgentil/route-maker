create extension postgis;


create table public.location(
 id serial primary key
 , name varchar(200)
 , description text
 , point geometry(POINT,3857)
);


alter table public.location add column point_name text;
alter table public.location add column private boolean default false;