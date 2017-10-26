alter table replies add column endorsed int;
alter table replies drop foreign key replies_ibfk_3;
alter table replies drop index endorsed_user_id;
alter table replies drop endorsed_user_id;

alter table threads add column endorsed int DEFAULT 0;
alter table threads drop foreign key threads_ibfk_3;
alter table threads drop index endorsed_user_id;
alter table threads drop endorsed_user_id;