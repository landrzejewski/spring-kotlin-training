create table users(username varchar(50) not null primary key, password varchar(100) not null, enabled boolean not null);
create table authorities (username varchar(50) not null, authority varchar(50) not null);

insert into users (username, password, enabled) values ('jan', '$2a$10$nmZB2dvjw.YbIRirM5WBXOrZy0YN0nyUTl/nTxDbOLSh2AZ6qYuQa', 1);
insert into authorities (username, authority) values ('jan', 'ROLE_ADMIN');
