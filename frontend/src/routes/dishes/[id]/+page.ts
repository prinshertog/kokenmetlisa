import { error } from '@sveltejs/kit';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ params, fetch }) => {
    const apiUrl = `http://localhost:8080/dishes/${params.id}`;

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