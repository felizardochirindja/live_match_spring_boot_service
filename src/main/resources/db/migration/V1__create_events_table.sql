create table events(
    id integer unsigned not null,
    name varchar(255) not null,
    description text,
    
    constraint pk_events primary key (id),
    constraint unq_name unique index (name asc) visible
) engine=innodb character set utf8mb4 collate utf8mb4_0900_ai_ci;