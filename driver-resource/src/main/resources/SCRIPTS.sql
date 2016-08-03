drop table if exists public.driver;
CREATE TABLE public.driver (
 id serial primary key,
 name varchar(200),
 home_location_id integer not null
);

alter table driver add constraint fk_driver_location foreign key ( home_location_id )references location (id );