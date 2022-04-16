create table character (
    player_id           varchar(30),
    base_id             numeric primary key,
    dynasty_prefix      varchar(10),
    dynasty_name        varchar(100),
    dynasty_motto       varchar(200),
    dynasty_coa         text,
    copy_coa_to_title   boolean,

    primary_character_name  varchar(100),
    is_female           boolean,
    sexual_orientation  varchar(10),
    culture_group       varchar(100),
    culture             varchar(100),
    primary_dna         text,

    diplomacy           integer,
    intrigue            integer,
    martial             integer,
    learning            integer,
    stewardship         integer,
    prowess             integer,

    spouse              boolean,
    children_age        varchar(100)

);

create table character_traits (
    base_id             numeric,
    trait               varchar(100),
    primary key (base_id, trait)
);

create table character_child (
    base_id         numeric,
    child_index     numeric,
    name            varchar(100),
    is_female       boolean,
    primary key (base_id, child_index)
);
