
drop table if exists public.route;
create table public.route(
  id bigserial primary key
  ,date timestamp not null
  ,starting_location_id int not null
  ,ending_location_id int not null
  ,driver_id int not null
  ,created_at timestamp not null
);

alter table public.route add constraint fk_route_starting_location foreign key ( starting_location_id ) references public.location( id );
alter table public.route add constraint fk_route_ending_location foreign key ( ending_location_id ) references public.location( id );
alter table public.route add constraint fk_route_driver foreign key ( driver_id ) references public.driver( id );



drop table if exists public.stop
create table public.stop
(
  id bigserial primary key
  ,route_id bigint not null
  ,stop_order int not null
  ,point geometry(POINT,3857) not null
);

alter table public.stop add constraint fk_stop_route foreign key ( route_id ) references public.route( id );