ALTER TABLE public.dishes
ADD COLUMN IF NOT EXISTS "position" integer;

WITH numbered AS (
    SELECT id, ROW_NUMBER() OVER (ORDER BY id) AS rn
    FROM public.dishes
)
UPDATE public.dishes d
SET "position" = numbered.rn
FROM numbered
WHERE d.id = numbered.id
  AND d."position" IS NULL;

ALTER TABLE public.dishes
ALTER COLUMN "position" SET NOT NULL;