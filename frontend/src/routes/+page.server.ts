import { env } from '$env/dynamic/private';
const BASE_URL_BACKEND = env.BASE_URL_BACKEND;
export async function load({}) {
    try {
        const response = await fetch(BASE_URL_BACKEND + '/dishes');
        if (!response.ok) {
            throw new Error(`Failed to fetch dishes: ${response.statusText}`);
        }

        const dishes = await response.json();
        return {
            dishes,
            BASE_URL_BACKEND
        };
    } catch (error) {
        console.error(error);
        return {
            error: 'Failed to load dishes'
        };
    }
}