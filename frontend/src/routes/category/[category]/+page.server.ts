import { error } from '@sveltejs/kit';
import { env } from '$env/dynamic/public';
const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ params, url, fetch }) => {
    try {
        const response = await fetch(`${BASE_URL_BACKEND}/dishes?page=${url.searchParams.get('page') ?? 0}&category=${params.category}`);
        if (!response.ok) {
            const errorData = await response.text();
            throw error(response.status, errorData);
        }
        const pageObject = await response.json();
        if (pageObject.content.length === 0) {
            throw error(404, "No dishes found");
        }

        if (pageObject.content.length === 0) {
            throw error(404, "No dishes found");
        }
        return { pageObject };

    } catch (err: any) {
        if (err.status && err.body) {
            throw err;
        }
        throw error(500, "Failed to fetch dishes");
    }
}