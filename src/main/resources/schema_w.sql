
create table account (
    user_id  bigint auto_increment ,
    name varchar(80) not null,
    password varchar(80) not null,
    email varchar(80) not null,
    address varchar(80) not null,
    country varchar(20) not null,
    phone varchar(80) not null,
    constraint pk_account primary key (user_id)
);