import { error } from '@sveltejs/kit';
import { env } from '$env/dynamic/public';
const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;
import type { PageServerLoad } from './$types';
import type { Dish } from '$lib/types/types';

export const load: PageServerLoad = async ({ params, fetch }) => {
    try {
        const response = await fetch(`${BASE_URL_BACKEND}/dishes`);
        if (!response.ok) {
            const errorData = await response.text();
            throw error(response.status, errorData);
        }
        const data: Dish[] = await response.json();
        if (data.length === 0) {
            throw error(404, "No dishes found");
        }
        const categoryDishes = data.filter(dish => {
                for (let category of dish.categories) {
                    if (category.name === params.category 
                        || category.parentCategory?.name === params.category) {
                            return dish;
                    }
                }
            }
        )
        if (categoryDishes.length === 0) {
            throw error(404, "No dishes found");
        }
        return { categoryDishes };

    } catch (err: any) {
        if (err.status && err.body) {
            throw err;
        }
        throw error(500, "Failed to fetch dishes");
    }
}