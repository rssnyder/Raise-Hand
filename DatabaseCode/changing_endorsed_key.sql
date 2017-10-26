alter table replies add column endorsed int;
alter table replies drop foreign key replies_ibfk_3;
alter table replies drop index endorsed_user_id;
alter table replies drop endorsed_user_id;