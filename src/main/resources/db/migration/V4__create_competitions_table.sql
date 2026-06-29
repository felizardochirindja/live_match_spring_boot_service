create table competitions(
    id int unsigned not null,
    title varchar(255) not null,
    description text,
    
    constraint pk_competitions primary key (id),
    constraint unq_title unique index  (title asc) visible
) engine=innodb character set utf8mb4 collate utf8mb4_0900_ai_ci;

create table competition_teams(
    team_id char(4) not null,
    competition_id int unsigned not null,

    constraint fk_teams_competition_teams foreign key (team_id) references teams (id),
    constraint fk_competition_teams_competitions foreign key (competition_id) references competitions (id)
) engine=innodb character set utf8mb4 collate utf8mb4_0900_ai_ci;