CREATE TABLE if not exists candidate (
	id SERIAL PRIMARY KEY,
	name text,
	experience double precision,
	salary integer
);