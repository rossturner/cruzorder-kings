create table player (
    player_id          varchar(30) primary key,
    discord_username    varchar(30),
    balance             double precision,
    total_points_earned double precision,
    ranking_score       double precision
);
alter table player add column is_admin boolean default false;
update player set is_admin = true where player_id = '291857466491273218'; -- Zsinj player ID
update player set is_admin = true where player_id = '149222835850706944'; -- Harringzord player ID

alter table player add column discord_avatar varchar(100);


create table audit_log (
   player_id          varchar(30),
   discord_username    varchar(30),
   datetime            timestamp,
   action              varchar(300),
   primary key (player_id, datetime)
);