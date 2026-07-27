CREATE TABLE inspiration_dishes (
                                    id BIGSERIAL PRIMARY KEY,
                                    title VARCHAR(255) NOT NULL,
                                    short_description VARCHAR(500),
                                    image_url VARCHAR(500),
                                    source_name VARCHAR(255) NOT NULL,
                                    external_url VARCHAR(1000) NOT NULL
);

CREATE TABLE inspiration_dishes_categories (
                                               inspiration_dish_id BIGINT NOT NULL,
                                               category_name VARCHAR(255) NOT NULL,

                                               PRIMARY KEY (inspiration_dish_id, category_name),

                                               CONSTRAINT fk_idc_dish
                                                   FOREIGN KEY (inspiration_dish_id)
                                                       REFERENCES inspiration_dishes(id)
                                                       ON DELETE CASCADE,

                                               CONSTRAINT fk_idc_category
                                                   FOREIGN KEY (category_name)
                                                       REFERENCES categories(name)
                                                       ON DELETE CASCADE
);