import { env } from '$env/dynamic/public';
import type { Dish } from '$lib/types/types.js';
const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;
export async function load({params}) {
    try {
        const response = await fetch(BASE_URL_BACKEND + `/dishes/page/${params.pageNumber}`);
        if (!response.ok) {
            throw new Error(`Failed to fetch dishes: ${response.statusText}`);
        }

        const dishes = await response.json();
        return {
            dishes
        };
    } catch (error) {
        console.error(error);
        return {
            error: 'Failed to load dishes'
        };
    }
}