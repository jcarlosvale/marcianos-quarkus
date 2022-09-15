CREATE TABLE USERS (
                       id bigserial not null primary key,
                       name varchar(100) not null,
                       cpf varchar(20) not null,
                       email varchar(50) not null
);