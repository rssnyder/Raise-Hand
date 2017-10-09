ALTER TABLE threads ADD COLUMN user_name varchar(100);
ALTER TABLE replies ADD COLUMN user_name varchar(100);
ALTER TABLE replies ADD COLUMN parent integer;