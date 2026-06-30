import { env } from '$env/dynamic/public';
import type { Category } from '$lib/types/types';
const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;
export async function load() {
    try {
        const categoriesResponse = await fetch(BASE_URL_BACKEND + "/category");

        if (!categoriesResponse.ok) {
            throw new Error(`Failed to fetch data: ${categoriesResponse.statusText}`);
        }

        const categories = await categoriesResponse.json() as Category[];
        const parentCategories: Category[] = categories
            .filter(c => c.parentCategory == null)
            .map(c => ({
                name: c.name,
                parentCategory: c.parentCategory,
                childCategories: categories.filter(sc => sc.parentCategory?.name == c.name)
            }))
        return { parentCategories };
    } catch (error) {
        console.error("Error fetching data:", error);
        return { error: "Failed to fetch categories" };
    }
}
