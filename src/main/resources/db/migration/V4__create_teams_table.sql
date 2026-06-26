create table teams(
    id char(4) not null,
    name varchar(255) not null,
    notes text,
    trainer_name varchar(255) not null,
    
    constraint pk_teams primary key (id),
    constraint unq_name unique index (name asc) visible
) engine=innodb character set utf8mb4 collate utf8mb4_0900_ai_ci;

create table team_players(
    team_id char(4) not null,
    player_id int unsigned not null,
    role enum('guarda_redes'),

    constraint fk_team_players_teams foreign key (team_id) references teams (id),
    constraint fk_players_team_players foreign key (player_id) references players (id)
) engine=innodb character set utf8mb4 collate utf8mb4_0900_ai_ci;