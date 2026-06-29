create table lineups(
    id int unsigned not null,
    initial boolean not null,
    position int unsigned not null,
    match_team_id int unsigned not null,
    
    constraint pk_lineups primary key (id),
    constraint fk_lineups_match_teams foreign key (match_team_id) references match_teams (id)
) engine=innodb character set utf8mb4 collate utf8mb4_0900_ai_ci;

create table lineup_players(
    lineup_id int unsigned not null,
    player_id int unsigned not null,
    role enum('guarda_redes'),

    constraint fk_lineup_players_lineups foreign key (lineup_id) references lineups (id),
    constraint fk_players_lineup_players foreign key (player_id) references players (id)
) engine=innodb character set utf8mb4 collate utf8mb4_0900_ai_ci;