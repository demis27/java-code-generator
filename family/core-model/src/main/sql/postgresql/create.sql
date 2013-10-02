create table family."user" (
    user_id             character varying(32),
    creation_date       timestamp without time zone,
    modification_date   timestamp without time zone,
    email               character varying(255),
    first_name          character varying(64),
    last_name           character varying(64),
    constraint user_pk primary key (user_id)
);

create table family.family_tree (
    family_tree_id      character varying(32),
    creation_date       timestamp without time zone,
    modification_date   timestamp without time zone,
    user_id             character varying(32) not null,
    constraint family_tree_pk primary key (family_tree_id),
    constraint family_tree_user_fk foreign key (user_id)
        references family."user" (user_id)
);