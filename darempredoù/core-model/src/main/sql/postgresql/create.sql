create table darempredoù."user_group" (
    user_group_id       character varying(32) not null,
    creation_date       timestamp without time zone default current_date,
    modification_date   timestamp without time zone default current_date,
    "name"              character varying(64),
    constraint user_group_pk primary key (user_group_id)
);

create table darempredoù."user_group_label" (
    user_group_label_id character varying(32) not null,
    creation_date       timestamp without time zone default current_date,
    modification_date   timestamp without time zone default current_date,
    user_group_id       character varying(32) not null,
    label               character varying(64),
    locale              character varying(5),
    constraint user_group_label_pk primary key (user_group_label_id),
    constraint user_group_fk foreign key (user_group_id)
        references darempredoù.user_group (user_group_id) match simple
        on update no action on delete no action
);

create table darempredoù."application_user" (
    user_id             character varying(32) not null,
    creation_date       timestamp without time zone default current_date,
    modification_date   timestamp without time zone default current_date,
    email               character varying(255),
    first_name          character varying(64),
    last_name           character varying(64),
    login               character varying(64) not null,
    password            character varying(64) not null,
    user_group_id       character varying(32) not null,
    constraint user_pk primary key (user_id),
    constraint unique_login unique (login),
    constraint user_group_fk foreign key (user_group_id)
        references darempredoù.user_group (user_group_id) match simple
        on update no action on delete no action
);

-- Group/Enterprise/Etablishment

create table darempredoù.enterprise_group (
	enterprise_group_id 		character varying(32) not null,
	creation_date       		timestamp without time zone default current_date,
	modification_date   		timestamp without time zone default current_date,
	name 				character varying(256) not null,
	constraint enterprise_group_pkey primary key (enterprise_group_id)
) ;

create table darempredoù.enterprise (
	enterprise_id 			character varying(32) not null,
	creation_date       		timestamp without time zone default current_date,
	modification_date   		timestamp without time zone default current_date,
	name 				character varying(256) not null,
	enterprise_group_id 		character varying(32),
	constraint enterprise_pkey primary key (enterprise_id),
	constraint group_enterprise foreign key (enterprise_group_id) references darempredoù.enterprise_group (enterprise_group_id)
) ;

create table darempredoù.etablishment (
	etablishment_id 		character varying(32) not null,
	creation_date       		timestamp without time zone default current_date,
	modification_date   		timestamp without time zone default current_date,
	name 				character varying(256) not null,
	enterprise_id 			character varying(32),
	constraint etablishment_pkey primary key (etablishment_id),
	constraint enterprise_etablishment foreign key (enterprise_id) references darempredoù.enterprise (enterprise_id)
) ;


