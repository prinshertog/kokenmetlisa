import { error } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';
import { env } from '$env/dynamic/public';
const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;

export const load: PageServerLoad = async ({ params, fetch }) => {
    const apiUrl = BASE_URL_BACKEND + `/dishes/${params.id}`;

    const response = await fetch(apiUrl);

    if (response.ok) {
        const data = await response.json();
        if (!data || data.length === 0) {
            throw error(404, 'Dish not found');
        }
        return {
            dish: data[0]
        };
    }

    throw error(404, 'Dish not found');
};