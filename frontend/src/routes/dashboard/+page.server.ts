import { fail, redirect } from '@sveltejs/kit';
import type { Actions, PageServerLoad } from './$types';
import { checkLogin } from '../../lib/methods/loginCheck';
import { env } from '$env/dynamic/public';
const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;

export const load: PageServerLoad = async ({ cookies }) => {
    await checkLogin(cookies);
    
    try {
        const username = cookies.get('username');
        const role = cookies.get('role');
        
        const [dishesResponse, categoriesResponse] = await Promise.all([
            fetch(BASE_URL_BACKEND + '/dishes'),
            fetch(BASE_URL_BACKEND + '/category', {
                headers: {
                    'Authorization': `Bearer ${cookies.get('bearer')}`
                }
            })
        ]);

        if (!dishesResponse.ok || !categoriesResponse.ok) {
            throw new Error('Failed to fetch data');
        }

        const [dishes, categories] = await Promise.all([
            dishesResponse.json(),
            categoriesResponse.json()
        ]);

        return { 
            dishes, 
            categories, 
            username,
            role
        };
    } catch (error) {
        console.error(error);
        return { 
            error: 'Failed to load data', 
            dishes: [], 
            categories: [],
            username: null,
            role: null
        };
    }
}

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
                category: data.get('category'),
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

    addCategory: async ({ request, cookies }) => {
        try {
            const data = await request.formData();
            const parentCategory = data.get('parentCategory');
            const categoryData = {
                category: data.get('category'),
                ...(parentCategory ? { parentCategory } : {})
            };

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
                body: JSON.stringify({ category })
            });

            if (!response.ok) {
                return fail(400, { categoryError: 'Failed to delete category' });
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
                    category: category,
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
                    category: category,
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