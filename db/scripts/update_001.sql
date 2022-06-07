create TABLE if not exists vacancy_db (
     id SERIAL PRIMARY KEY,
	name text
);

create TABLE if not exists candidates (
	id SERIAL PRIMARY KEY,
	name text,
	experience double precision,
	salary integer,
	db_id int not null references vacancy_db(id)
);

create TABLE if not exists vacancies (
    id SERIAL PRIMARY KEY,
    description text,
    created timestamp without time zone
);

create TABLE if not exists vacancy_db_vacancies (
    id SERIAL PRIMARY KEY,
    dbofvacancy_id integer not null references vacancy_db(id),
    vacancies_id integer not null references vacancies(id)
);







