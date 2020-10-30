CREATE TABLE players(
    id          INT             AUTO_INCREMENT,
    username    VARCHAR(20)     NOT NULL,
    password    VARBINARY(20)   NOT NULL,
    xp          INT             NOT NULL,
    wins        INT             NOT NULL,
    losses      INT             NOT NULL,
    CONSTRAINT players_id_uindex
        UNIQUE (id)
);

ALTER TABLE players
    ADD CONSTRAINT players_id_pk PRIMARY KEY(id);

