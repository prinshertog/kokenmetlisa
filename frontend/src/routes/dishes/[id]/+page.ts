import { error } from '@sveltejs/kit';
import type { PageLoad } from './$types';
const BACKEND_URL = "http://localhost:8080";

export const load: PageLoad = async ({ params, fetch }) => {
    const apiUrl = BACKEND_URL + `/dishes/${params.id}`;

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