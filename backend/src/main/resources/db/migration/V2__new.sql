ALTER TABLE IF EXISTS dishes
    DROP CONSTRAINT IF EXISTS fkpjk1tscss4s2ab2a5tvn4t50x;

CREATE TABLE IF NOT EXISTS dishes_categories
(
    dish_id             BIGINT       NOT NULL,
    categories_category VARCHAR(255) NOT NULL
);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints tc
        WHERE tc.constraint_type = 'FOREIGN KEY'
          AND tc.table_name = 'dishes_categories'
          AND tc.constraint_name = 'fk_dishes_categories_dish'
    ) THEN
        ALTER TABLE dishes_categories
            ADD CONSTRAINT fk_dishes_categories_dish FOREIGN KEY (dish_id) REFERENCES dishes (id) ON DELETE NO ACTION;
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints tc
        WHERE tc.constraint_type = 'FOREIGN KEY'
          AND tc.table_name = 'dishes_categories'
          AND tc.constraint_name = 'fk_dishes_categories_category'
    ) THEN
        ALTER TABLE dishes_categories
            ADD CONSTRAINT fk_dishes_categories_category FOREIGN KEY (categories_category) REFERENCES categories (category) ON DELETE NO ACTION;
    END IF;
END
$$;

ALTER TABLE IF EXISTS dishes
    DROP COLUMN IF EXISTS category_category;