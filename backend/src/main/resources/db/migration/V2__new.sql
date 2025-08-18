ALTER TABLE dishes
    DROP CONSTRAINT fkpjk1tscss4s2ab2a5tvn4t50x;

CREATE TABLE dishes_categories
(
    dish_id             BIGINT       NOT NULL,
    categories_category VARCHAR(255) NOT NULL
);

ALTER TABLE dishes_categories
    ADD CONSTRAINT fk_dishes_categories_dish FOREIGN KEY (dish_id) REFERENCES dishes (id) ON DELETE NO ACTION;

ALTER TABLE dishes_categories
    ADD CONSTRAINT fk_dishes_categories_category FOREIGN KEY (categories_category) REFERENCES categories (category) ON DELETE NO ACTION;

ALTER TABLE dishes
    DROP COLUMN category_category;