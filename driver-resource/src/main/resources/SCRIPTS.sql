CREATE TABLE driver (
 id serial not null,
 name varchar(200),
 location_id integer not null
);

alter table driver add constraint fk_driver_location foreign key ( location_id )references location (id );