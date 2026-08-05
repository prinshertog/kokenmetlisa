import { fail, redirect } from '@sveltejs/kit';
import type { Actions, PageServerLoad } from './$types';
import { env } from '$env/dynamic/public';
import { checkLogin } from '$lib/methods/loginCheck';
const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;

export const load: PageServerLoad = async ({ cookies }) => {
    await checkLogin(cookies);
    try {
        const username = cookies.get('username');
        const role = cookies.get('role');

        const [categoriesResponse, inspirationResponse] = await Promise.all([
            fetch(BASE_URL_BACKEND + '/category', {
                headers: {
                    'Authorization': `Bearer ${cookies.get('bearer')}`
                }
            }),
            fetch(BASE_URL_BACKEND + '/inspirationDishes?page=0')
        ]);

        if (!categoriesResponse.ok) {
            throw new Error('Failed to fetch category data');
        }

        if (!inspirationResponse.ok) {
            throw new Error('Failed to fetch inspiration dishes data');
        }

        const [categories, pageObject] = await Promise.all([
            categoriesResponse.json(),
            inspirationResponse.json()
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

export const actions: Actions = {
    addInspirationDish: async ({ request, cookies }) => {
        try {
            const data = await request.formData();
            const title = data.get('title') as string;
            const shortDescription = data.get('shortDescription') as string;
            const imageUrl = data.get('imageUrl') as string;
            const sourceName = data.get('sourceName') as string;
            const externalUrl = data.get('externalUrl') as string;
            const categories = data.getAll('selected-categories').map(String);

            const payload = {
                title,
                shortDescription,
                imageUrl,
                sourceName,
                externalUrl,
                categories
            };

            const bearer = cookies.get('bearer');
            const response = await fetch(`${BASE_URL_BACKEND}/inspirationDishes`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${bearer}`
                },
                body: JSON.stringify(payload)
            });

            if (!response.ok) {
                const errorText = await response.json();
                return fail(400, { inspirationDishError: errorText.error || 'Failed to create inspiration dish' });
            }

            return { inspirationDishSuccess: true };
        } catch (error) {
            return fail(500, {
                inspirationDishError: error instanceof Error ? error.message : 'An unknown error occurred'
            });
        }
    },

    deleteInspirationDish: async ({ request, cookies }) => {
        try {
            const data = await request.formData();
            const id = data.get('id');
            const bearer = cookies.get('bearer');

            const response = await fetch(`${BASE_URL_BACKEND}/inspirationDishes/${id}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${bearer}`
                }
            });

            if (!response.ok) {
                const errorText = await response.json();
                return fail(400, { inspirationDishError: errorText.error || 'Failed to delete inspiration dish' });
            }

            return { inspirationDishSuccess: true };
        } catch (error) {
            return fail(500, {
                inspirationDishError: error instanceof Error ? error.message : 'An unknown error occurred'
            });
        }
    },

    updateInspirationDish: async ({ request, cookies }) => {
        try {
            const data = await request.formData();
            const id = data.get('id');
            const title = data.get('title') as string;
            const shortDescription = data.get('shortDescription') as string;
            const imageUrl = data.get('imageUrl') as string;
            const sourceName = data.get('sourceName') as string;
            const externalUrl = data.get('externalUrl') as string;
            const categories = data.getAll('selected-categories').map(String);

            const payload = {
                title,
                shortDescription,
                imageUrl,
                sourceName,
                externalUrl,
                categories
            };

            const bearer = cookies.get('bearer');
            const response = await fetch(`${BASE_URL_BACKEND}/inspirationDishes/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${bearer}`
                },
                body: JSON.stringify(payload)
            });

            if (!response.ok) {
                const errorText = await response.json();
                return fail(400, { inspirationDishError: errorText.error || 'Failed to update inspiration dish' });
            }

            return { inspirationDishSuccess: true };
        } catch (error) {
            return fail(500, {
                inspirationDishError: error instanceof Error ? error.message : 'An unknown error occurred'
            });
        }
    },

    logout: async ({ cookies }) => {
        cookies.delete('bearer', { path: '/' });
        cookies.delete('username', { path: '/' });
        cookies.delete('role', { path: '/' });
        throw redirect(303, '/login');
    }
};