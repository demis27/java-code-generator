create sequence darempredoù.user_group_sequence;
create table darempredoù.user_group (
    user_group_id       integer default nextval ('darempredoù.user_group_sequence') not null,
    creation_date       timestamp without time zone default current_date,
    modification_date   timestamp without time zone default current_date,
    "name"              character varying(64),
    constraint user_group_pk primary key (user_group_id)
);

create sequence darempredoù.user_group_label_sequence;
create table darempredoù.user_group_label (
    user_group_label_id integer default nextval ('darempredoù.user_group_label_sequence') not null,
    creation_date       timestamp without time zone default current_date,
    modification_date   timestamp without time zone default current_date,
    user_group_id       integer not null,
    label               character varying(64),
    locale              character varying(5),
    constraint user_group_label_pk primary key (user_group_label_id),
    constraint user_group_fk foreign key (user_group_id)
        references darempredoù.user_group (user_group_id) match simple
        on update no action on delete no action
);

create sequence darempredoù.application_user_sequence;
create table darempredoù."application_user" (
    user_id             integer default nextval ('darempredoù.application_user_sequence') not null,
    creation_date       timestamp without time zone default current_date,
    modification_date   timestamp without time zone default current_date,
    email               character varying(255),
    first_name          character varying(64),
    last_name           character varying(64),
    login               character varying(64) not null,
    password            character varying(64) not null,
    user_group_id       integer not null,
    constraint user_pk primary key (user_id),
    constraint unique_login unique (login),
    constraint user_group_fk foreign key (user_group_id)
        references darempredoù.user_group (user_group_id) match simple
        on update no action on delete no action
);

-- Group/Enterprise/Etablishment

create sequence darempredoù.enterprise_group_sequence;
create table darempredoù.enterprise_group (
	enterprise_group_id 		integer default nextval ('darempredoù.enterprise_group_sequence') not null,
	creation_date       		timestamp without time zone default current_date,
	modification_date   		timestamp without time zone default current_date,
	"name" 				        character varying(256) not null,
	constraint enterprise_group_pkey primary key (enterprise_group_id)
) ;

create sequence darempredoù.enterprise_sequence;
create table darempredoù.enterprise (
	enterprise_id 			    integer default nextval ('darempredoù.enterprise_sequence') not null,
	creation_date       		timestamp without time zone default current_date,
	modification_date   		timestamp without time zone default current_date,
	"name" 				        character varying(256) not null,
	enterprise_group_id 		integer,
	constraint enterprise_pkey primary key (enterprise_id),
	constraint group_enterprise foreign key (enterprise_group_id) references darempredoù.enterprise_group (enterprise_group_id)
) ;

create sequence darempredoù.etablishment_sequence;
create table darempredoù.etablishment (
	etablishment_id 		    integer default nextval ('darempredoù.etablishment_sequence') not null,
	creation_date       		timestamp without time zone default current_date,
	modification_date   		timestamp without time zone default current_date,
	"name" 				        character varying(256) not null,
	enterprise_id 			    integer,
	constraint etablishment_pkey primary key (etablishment_id),
	constraint enterprise_etablishment foreign key (enterprise_id) references darempredoù.enterprise (enterprise_id)
) ;


-- Message / Mail

create table darempredoù.mailserver_protocol (
    protocol_id                 integer not null,
    "name"                      character varying(8) not null,
	constraint mailserver_protocol_pkey primary key (protocol_id)
);

insert into darempredoù.mailserver_protocol values (1, 'POP3');
insert into darempredoù.mailserver_protocol values (2, 'POP3S');
insert into darempredoù.mailserver_protocol values (3, 'IMAP4');
insert into darempredoù.mailserver_protocol values (4, 'IMAP4S');

create sequence darempredoù.mail_server_sequence;
create table darempredoù.mail_server (
	mail_server_id 		        integer default nextval ('darempredoù.mail_server_sequence') not null,
	creation_date       		timestamp without time zone default current_date,
	modification_date   		timestamp without time zone default current_date,
	"name" 				        character varying(256) not null,
    protocol_id                 integer not null,
    host                        character varying(256) not null,
    port                        integer not null,
	constraint mail_server_pkey primary key (mail_server_id)
--	,constraint mailserver_protocol foreign key (protocol_id) references darempredoù.mailserver_protocol (protocol_id)
);

create sequence darempredoù.mailbox_sequence;
create table darempredoù.mailbox (
	mailbox_id 		            integer default nextval ('darempredoù.mailbox_sequence') not null,
	creation_date       		timestamp without time zone default current_date,
	modification_date   		timestamp without time zone default current_date,
	"name" 				        character varying(256) not null,
    login                       character varying(64) not null,
    password                    character varying(64) not null,
    email_address               character varying(256) not null,
	constraint mailbox_pkey primary key (mailbox_id)
--	,constraint mailserver_protocol foreign key (protocol_id) references darempredoù.mailserver_protocol (protocol_id)
);
