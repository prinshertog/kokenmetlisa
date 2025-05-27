import { fail, redirect } from '@sveltejs/kit';
import type { Actions } from './$types';

const BACKEND_URL = "http://localhost:8080";

export async function load({ cookies }) {
    const bearer = cookies.get('bearer');
    if (!bearer) {
        throw redirect(303, '/login');
    }

    try {
        const username = cookies.get('username');
        const role = cookies.get('role');
        
        const [dishesResponse, categoriesResponse] = await Promise.all([
            fetch(BACKEND_URL + '/dishes'),
            fetch(BACKEND_URL + '/category', {
                headers: {
                    'Authorization': `Bearer ${bearer}`
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
            const dishData = {
                name: data.get('name'),
                description: data.get('description'),
                category: data.get('category'),
                subcategory: data.get('subcategory'),
                imageUrl: data.get('imageUrl')
            };

            const bearer = cookies.get('bearer');
            const response = await fetch(BACKEND_URL + '/dishes', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${bearer}`
                },
                body: JSON.stringify(dishData)
            });

            if (!response.ok) {
                const errorText = await response.json();
                return fail(400, { error: errorText.error || 'Failed to add dish' });
            }

            return { success: true };
        } catch (error) {
            return fail(500, {
                error: error instanceof Error ? error.message : 'An unknown error occurred'
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
            const response = await fetch(BACKEND_URL + '/category', {
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

            const response = await fetch(`${BACKEND_URL}/dishes/${id}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${bearer}`
                }
            });

            if (!response.ok) {
                return fail(400, { error: 'Failed to delete dish' });
            }

            return { success: true, dishDeleted: true };
        } catch (error) {
            return fail(500, { error: 'Failed to delete dish' });
        }
    },

    deleteCategory: async ({ request, cookies }) => {
        try {
            const data = await request.formData();
            const category = data.get('category');
            const bearer = cookies.get('bearer');

            const response = await fetch(`${BACKEND_URL}/category`, {
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
    }
} satisfies Actions;