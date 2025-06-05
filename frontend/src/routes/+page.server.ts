import { BASE_URL_BACKEND } from '$env/static/private';
export async function load({}) {
    try {
        const response = await fetch(BASE_URL_BACKEND + '/dishes');
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