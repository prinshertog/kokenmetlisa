import { fail, redirect } from '@sveltejs/kit';
import type { Actions, PageServerLoad } from './$types';
import { env } from '$env/dynamic/public';
import type { CreateCategory } from '$lib/types/types';
import { checkLogin } from '$lib/methods/loginCheck';
const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;

export const load: PageServerLoad = async ({ cookies }) => {
    await checkLogin(cookies);
    try {
        const username = cookies.get('username');
        const role = cookies.get('role');

        const [categoriesResponse] = await Promise.all([
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

        const [categories] = await Promise.all([
            categoriesResponse.json(),
        ]);

        return {
            categories,
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
    logout: async ({ cookies }) => {
        cookies.delete('bearer', { path: '/' });
        cookies.delete('username', { path: '/' });
        cookies.delete('role', { path: '/' });
        throw redirect(303, '/login');
    },

    addCategory: async ({ request, cookies }) => {
        try {
            const data = await request.formData();
            const parentCategory = data.get('parentCategory') as string;
            const categoryData: CreateCategory = {
                name: data.get('category') as string
            };

            if (parentCategory) {
                categoryData.parentCategory = parentCategory;
            }

            const bearer = cookies.get('bearer');
            const response = await fetch(BASE_URL_BACKEND + '/category', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${bearer}`
                },
                body: JSON.stringify(categoryData)
            });

            if (!response.ok) {
                const errorText = await response.json();
                return fail(400, { categoryError: errorText.error || 'Failed to add category' });
            }

            return { categorySuccess: true };
        } catch (error) {
            return fail(500, {
                categoryError: error instanceof Error ? error.message : 'An unknown error occurred'
            });
        }
    },

    deleteCategory: async ({ request, cookies }) => {
        try {
            const data = await request.formData();
            const category = data.get('category');
            const bearer = cookies.get('bearer');

            const response = await fetch(`${BASE_URL_BACKEND}/category`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${bearer}`
                },
                body: category
            });

            console.log(response.status);

            if (!response.ok) {
                let error = await response.json()
                return fail(400, { categoryError: error.error ?? "Failed to delete category"});
            }

            return { categorySuccess: true, categoryDeleted: true };
        } catch (error) {
            return fail(500, { categoryError: 'Failed to delete category' });
        }
    },

    down: async ({ request, cookies }) => {
        try {
            const data = await request.formData();
            const category = data.get('category');
            const bearer = cookies.get('bearer');
            const response = await fetch(`${BASE_URL_BACKEND}/category/position`, {
                method: "PUT",
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${bearer}`
                },
                body: JSON.stringify({ 
                    name: category,
                    up: false
                })
            })

            if (!response.ok) {
                const error = await response.json();
                return fail(400, { categoryError:  error.error })
            }

            return { categorySuccess: true };
        } catch (error) {
            return fail(500, { categoryError: (error instanceof Error ? error.message : "Something went wrong.") })
        }
    },

    up: async ({ request, cookies }) => {
        try {
            const data = await request.formData();
            const category = data.get('category');
            const bearer = cookies.get('bearer');
            const response = await fetch(`${BASE_URL_BACKEND}/category/position`, {
                method: "PUT",
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${bearer}`
                },
                body: JSON.stringify({ 
                    name: category,
                    up: true
                })
            })

            if (!response.ok) {
                const error = await response.json();
                return fail(400, { categoryError:  error.error })
            }

            return { categorySuccess: true };
        } catch (error) {
            return fail(500, { categoryError: (error instanceof Error ? error.message : "Something went wrong.") })
        }
    }
} satisfies Actions;