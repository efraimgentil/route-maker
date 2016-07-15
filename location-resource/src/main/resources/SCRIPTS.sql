create extension postgis;


create table public.location(
 id serial primary key
 , name varchar(200)
 , description text
 , point geometry(POINT,3857)
);