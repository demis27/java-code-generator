create table darempredoù."user" (
    user_id             character varying(32) not null,
    creation_date       timestamp without time zone,
    modification_date   timestamp without time zone,
    email               character varying(255),
    first_name          character varying(64),
    last_name           character varying(64),
    login               character varying(64) not null,
    password            character varying(64) not null,
    constraint user_pk primary key (user_id)
);

