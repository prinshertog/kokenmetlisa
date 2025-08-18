ALTER TABLE dishes
    DROP CONSTRAINT fkpjk1tscss4s2ab2a5tvn4t50x;

CREATE TABLE dishes_categories
(
    dish_id             BIGINT       NOT NULL,
    categories_category VARCHAR(255) NOT NULL
);

CREATE TABLE flyway_schema_history
(
    installed_rank INTEGER                                   NOT NULL,
    version        VARCHAR(50),
    description    VARCHAR(200)                              NOT NULL,
    type           VARCHAR(20)                               NOT NULL,
    script         VARCHAR(1000)                             NOT NULL,
    checksum       INTEGER,
    installed_by   VARCHAR(100)                              NOT NULL,
    installed_on   TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    execution_time INTEGER                                   NOT NULL,
    success        BOOLEAN                                   NOT NULL,
    CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank)
);

CREATE INDEX flyway_schema_history_s_idx ON flyway_schema_history (success);

ALTER TABLE dishes_categories
    ADD CONSTRAINT fk_dishes_categories_dish FOREIGN KEY (dish_id) REFERENCES dishes (id) ON DELETE NO ACTION;

ALTER TABLE dishes_categories
    ADD CONSTRAINT fk_dishes_categories_category FOREIGN KEY (categories_category) REFERENCES categories (category) ON DELETE NO ACTION;

ALTER TABLE dishes
    DROP COLUMN category_category;