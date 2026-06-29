DO $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = 'public'
          AND table_name = 'categories'
          AND column_name = 'category'
    ) THEN
        EXECUTE 'ALTER TABLE public.categories RENAME COLUMN category TO name';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = 'public'
          AND table_name = 'categories'
          AND column_name = 'parent_category_category'
    ) THEN
        EXECUTE 'ALTER TABLE public.categories RENAME COLUMN parent_category_category TO parent_category_name';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = 'public'
          AND table_name = 'dishes_categories'
          AND column_name = 'categories_category'
    ) THEN
        EXECUTE 'ALTER TABLE public.dishes_categories RENAME COLUMN categories_category TO categories_name';
    END IF;
END$$;

ALTER TABLE public.dishes_categories
    DROP CONSTRAINT IF EXISTS fk_dishes_categories_category;

ALTER TABLE public.dishes_categories
    DROP CONSTRAINT IF EXISTS fk_dishes_categories_dish;

ALTER TABLE public.categories
    DROP CONSTRAINT IF EXISTS fkbwml8h4ebs1252ixtbbewxuew;

ALTER TABLE public.categories
    DROP CONSTRAINT IF EXISTS categories_pkey;

ALTER TABLE public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (name);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints tc
        WHERE tc.constraint_schema = 'public'
          AND tc.table_name = 'categories'
          AND tc.constraint_name = 'fk_categories_parent'
    ) THEN
        EXECUTE 'ALTER TABLE public.categories ADD CONSTRAINT fk_categories_parent FOREIGN KEY (parent_category_name) REFERENCES public.categories(name)';
    END IF;
END$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints tc
        WHERE tc.constraint_schema = 'public'
          AND tc.table_name = 'dishes_categories'
          AND tc.constraint_name = 'fk_dishes_categories_dish'
    ) THEN
        EXECUTE 'ALTER TABLE public.dishes_categories ADD CONSTRAINT fk_dishes_categories_dish FOREIGN KEY (dish_id) REFERENCES public.dishes(id) ON DELETE NO ACTION';
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints tc
        WHERE tc.constraint_schema = 'public'
          AND tc.table_name = 'dishes_categories'
          AND tc.constraint_name = 'fk_dishes_categories_category'
    ) THEN
        EXECUTE 'ALTER TABLE public.dishes_categories ADD CONSTRAINT fk_dishes_categories_category FOREIGN KEY (categories_name) REFERENCES public.categories(name) ON DELETE NO ACTION';
    END IF;
END$$;
