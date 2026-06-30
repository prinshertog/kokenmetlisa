-- =========================
-- USERS
-- =========================
CREATE TABLE users (
                       username VARCHAR(255) PRIMARY KEY,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(255) NOT NULL
);

-- =========================
-- CATEGORIES (self-referencing)
-- =========================
CREATE TABLE categories (
                            name VARCHAR(255) PRIMARY KEY,
                            parent_category_name VARCHAR(255),
                            position INTEGER NOT NULL,

                            CONSTRAINT fk_category_parent
                                FOREIGN KEY (parent_category_name)
                                    REFERENCES categories(name)
                                    ON DELETE SET NULL
);

CREATE INDEX idx_categories_parent
    ON categories(parent_category_name);

-- =========================
-- DISHES
-- =========================
CREATE TABLE dishes (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        description VARCHAR(10000) NOT NULL,
                        image_name VARCHAR(255),
                        position INTEGER NOT NULL
);

-- =========================
-- DISHES ↔ CATEGORIES (Many-to-Many)
-- =========================
CREATE TABLE dishes_categories (
                                   dish_id BIGINT NOT NULL,
                                   category_name VARCHAR(255) NOT NULL,

                                   PRIMARY KEY (dish_id, category_name),

                                   CONSTRAINT fk_dc_dish
                                       FOREIGN KEY (dish_id)
                                           REFERENCES dishes(id)
                                           ON DELETE CASCADE,

                                   CONSTRAINT fk_dc_category
                                       FOREIGN KEY (category_name)
                                           REFERENCES categories(name)
                                           ON DELETE CASCADE
);

CREATE INDEX idx_dc_dish ON dishes_categories(dish_id);
CREATE INDEX idx_dc_category ON dishes_categories(category_name);