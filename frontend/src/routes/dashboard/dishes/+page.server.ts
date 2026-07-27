import { fail, redirect } from '@sveltejs/kit';
import type { Actions, PageServerLoad } from '../$types';
import { env } from '$env/dynamic/public';
import { checkLogin } from '$lib/methods/loginCheck';
const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;

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

export const actions = {
    add: async ({ request, cookies }) => {
        try {
            const data = await request.formData();
            const image = data.get('image') as File;

            if (image.size <= 0) {
                return fail(400, { dishError: "You must provide an image." })
            }

            const filename = `${Date.now()}-${image.name}`;
            const dishData = {
                name: data.get('name'),
                description: data.get('description'),
                categories: data.getAll('selected-categories'),
                imageName: filename
            };

            const bearer = cookies.get('bearer');
            const formData = new FormData();
            formData.append("file", image);
            formData.append("dishRequest", new Blob([JSON.stringify(dishData)], { type: "application/json" }));

            const response = await fetch(BASE_URL_BACKEND + '/dishes', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${bearer}`
                },
                body: formData
            });

            if (!response.ok) {
                const errorText = await response.json();
                return fail(400, { dishError: errorText.error || 'Failed to add dish' });
            }
            
            return { success: true };

            
        } catch (error) {
            return fail(500, {
                dishError: error instanceof Error ? error.message : 'An unknown error occurred'
            });
        }
    },
    
    logout: async ({ cookies }) => {
        cookies.delete('bearer', { path: '/' });
        cookies.delete('username', { path: '/' });
        cookies.delete('role', { path: '/' });
        throw redirect(303, '/login');
    },

    deleteDish: async ({ request, cookies }) => {
        try {
            const data = await request.formData();
            const id = data.get('id');
            const bearer = cookies.get('bearer');

            const response = await fetch(`${BASE_URL_BACKEND}/dishes/${id}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${bearer}`
                }
            });

            if (!response.ok) {
                const error = await response.json()
                return fail(400, { error: 'Failed to delete dish' + error.message });
            }

            return { success: true, dishDeleted: true };
        } catch (error) {
            return fail(500, { error: 'Failed to delete dish' + (error instanceof Error ? error.message : 'An unknown error occurred') });
        }
    },
} satisfies Actions;