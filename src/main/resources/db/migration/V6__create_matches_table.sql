create table matches(
    id int unsigned not null,
    title varchar(255) not null,
    notes text,
    stage_id integer unsigned not null,
    
    constraint pk_matches primary key (id),
    constraint fk_matches_stages foreign key (stage_id) references stages (id)
) engine=innodb character set utf8mb4 collate utf8mb4_0900_ai_ci;

create table match_teams(
    id int unsigned not null,
    match_id int unsigned not null,
    team_id char(4) not null,
    principal boolean,

    constraint pk_match_teams primary key (id),
    constraint fk_match_teams_matches foreign key (match_id) references matches (id),
    constraint fk_teams_match_teams foreign key (team_id) references teams (id)
) engine=innodb character set utf8mb4 collate utf8mb4_0900_ai_ci;

create table match_events(
    match_id int unsigned not null,
    event_id int unsigned not null,
    event_minute varchar(8) not null,
    position int not null,
    payload json not null,

    constraint fk_match_events_matches foreign key (match_id) references matches (id),
    constraint fk_events_match_events foreign key (event_id) references events (id)
) engine=innodb character set utf8mb4 collate utf8mb4_0900_ai_ci;