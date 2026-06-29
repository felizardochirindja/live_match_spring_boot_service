create table stages(
    id int unsigned not null,
    name varchar(255) not null,
    description text,
    competition_id integer unsigned not null,
    
    constraint pk_stages primary key (id),
    constraint fk_stages_competitions foreign key (competition_id) references competitions (id),
    constraint unq_name unique index (name asc) visible
) engine=innodb character set utf8mb4 collate utf8mb4_0900_ai_ci;