import { env } from '$env/dynamic/public';
const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;
export async function load({url}) {
    try {
        const response = await fetch(BASE_URL_BACKEND + `/dishes?page=${url.searchParams.get('page') ?? 0}`);
        if (!response.ok) {
            throw new Error(`Failed to fetch dishes: ${response.statusText}`);
        }

        const pageObject = await response.json();
        return {
            pageObject,
            BASE_URL_BACKEND
        };
    } catch (error) {
        console.error(error);
        return {
            error: 'Failed to load dishes'
        };
    }
}