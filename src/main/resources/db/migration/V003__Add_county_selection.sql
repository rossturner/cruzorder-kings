create table territory_selection (
    territory_id        numeric primary key,
    dynasty_id          numeric,
    display_name        varchar(200),
    title_list          varchar(300),
    admin_only          boolean
);

INSERT INTO territory_selection (territory_id, display_name, title_list, admin_only)
VALUES
    (1, 'Ribe & Slesvig', 'c_ribe,c_slesvig', false),
    (2, 'Fyn & Vorbasse', 'c_fyn,c_vorbasse', false),
    (3, 'Ringkøbing & Viborg', 'c_ringkobing,c_viborg', false),
    (4, 'Aalborg & Aarhus', 'c_aalborg,c_aarhus', false),
    (5, 'Sjaælland & Lolland-Falster', 'c_sjaelland,c_lolland_falster', false),
    (6, 'Skane & Bornholm', 'c_skane,c_bornholm', false),
    (7, 'Halland & Finnveden', 'c_halland,c_finnveden', false),
    (8, 'Blekinge & Varend', 'c_blekinge,c_varend', false),
    (9, 'Öland & Möre', 'c_oland,c_more', false),
    (10, 'Gotland & Sevede', 'c_gotland,c_sevede', false),
    (11, 'Njudung & Västergötland', 'c_njudung,c_vastergotland', false),
    (12, 'Álfheimar & Dal', 'c_raniriki,c_dal', false),
    (13, 'Nordmark & Vingulmörk', 'c_nordmark,c_vingulmork', false),
    (14, 'Vestfold & Þelamörk', 'c_vestfold,c_telemark', false),
    (15, 'Austr Agðir & Vestr Agðir', 'c_nedenes,c_agdeside', false),
    (16, 'Rogaland & Hörðaland', 'c_rogalandi,c_hordalandi', false),
    (17, 'Sogn & Firðafylki', 'c_sogn,c_firdafylki', false),
    (18, 'Mæri & Guðbrandsdalir', 'c_norwegian_more,c_gudbrandsdalir', false),
    (19, 'Þrándheimr & Gauldælafylki', 'c_trandheim,c_gauldala', false),
    (20, 'Naumdælafylki & Boðin', 'c_namdalfylki,c_bothin', false),
    (21, 'Medelpad & Mórar', 'c_medelapd,c_morarna', false),
    (22, 'Ångermanland & Helgum', 'c_angermanland,c_helgum', false),
    (23, 'Finland & Tavasts', 'c_finland,c_tavasts', false),
    (24, 'Satakunta & Messukylä', 'c_satakunta,c_messukyla', false),
    (25, 'Mustasaari & Pietarsaari', 'c_mustasaari,c_pedersore', false),
    (26, 'Nyland & Viipuri', 'c_nyland,c_viipuri', false),
    (27, 'The Papacy', 'k_papal_state,k_romagna,c_roma,c_viterbo,c_tivoli', true)
;