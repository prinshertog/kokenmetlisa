import { env } from '$env/dynamic/public';
import { checkLogin } from '$lib/methods/loginCheck';
const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;
import type { PageServerLoad } from '../$types';

export const load: PageServerLoad = async ({ cookies }) => {
    await checkLogin(cookies);
    try {
        const username = cookies.get('username');
        const role = cookies.get('role');

        const [categoriesResponse, dishesResponse] = await Promise.all([
        fetch(BASE_URL_BACKEND + '/category', {
            headers: {
            'Authorization': `Bearer ${cookies.get('bearer')}`
            }
        }),
        fetch(BASE_URL_BACKEND + '/dishes?page=0&size=20')
        ]);

        if (!categoriesResponse.ok) {
        throw new Error('Failed to fetch category data');
        }

        if (!dishesResponse.ok) {
        throw new Error('Failed to fetch dishes data');
        }

        const [categories, pageObject] = await Promise.all([
        categoriesResponse.json(),
        dishesResponse.json()
        ]);

        return {
        categories,
        pageObject,
        username,
        role
        };
    } catch (error) {
        console.error(error);
        return {
        error: 'Failed to load data',
        pageObject: null,
        categories: [],
        username: null,
        role: null
        };
    }
};