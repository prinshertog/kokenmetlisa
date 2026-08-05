import { env } from '$env/dynamic/public';
const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;

export async function load({ url }) {
    try {
        const page = url.searchParams.get('page') ?? '0';
        const response = await fetch(`${BASE_URL_BACKEND}/inspirationDishes?page=${page}`);

        if (!response.ok) {
            throw new Error(`Failed to fetch inspiration dishes: ${response.statusText}`);
        }

        const pageObject = await response.json();
        return {
            pageObject
        };
    } catch (error) {
        console.error(error);
        return {
            pageObject: {
                content: [],
                first: true,
                last: true,
                number: 0
            }
        };
    }
}
