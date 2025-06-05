import { error } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';
import { BASE_URL_BACKEND } from '$env/static/private';

export const load: PageServerLoad = async ({ params, fetch }) => {
    const apiUrl = BASE_URL_BACKEND + `/dishes/${params.id}`;

    const response = await fetch(apiUrl);

    if (response.ok) {
        const data = await response.json();
        if (!data || data.length === 0) {
            throw error(404, 'Dish not found');
        }
        return data[0];
    }

    throw error(404, 'Dish not found');
};