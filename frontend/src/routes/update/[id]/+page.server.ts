const BASE_URL_BACKEND = import.meta.env.VITE_BASE_URL_BACKEND;
import { fail, redirect } from '@sveltejs/kit';

interface Category {
    category: string,
    parentCategory: Category | null
}

interface dishUpdateRequest {
    id: number,
    dishName: string,
    description: string,
    category: Category
}

interface Dish {
    id: number,
    name: string,
    description: string,
    category: Category
    imageName: string
}

export const actions = {
    update: async ({ params, fetch }) => {
        const apiUrl = BASE_URL_BACKEND + `/dishes/update/${params.id}`;
        const response = await fetch(apiUrl);
        const data = await response.json();
        if (response.ok) {
            if (!data || data.length === 0) {
                return fail(400, {error: data.error || 'Failed to update dish'})
            }
            return "Dish was updated successfully"
        }
        return fail(400, {error: "Failed to update dish." })
    }
}

export async function load({ cookies, params }) {
    const bearer = cookies.get('bearer');
    if (!bearer) {
        throw redirect(303, '/login');
    }

    try {
        const response = await fetch(BASE_URL_BACKEND + "/login", {
            headers: {
                'Authorization': `Bearer ${bearer}`
            }
        });
        
        if (!response.ok) {
            cookies.delete('bearer', { path: '/' });
            cookies.delete('username', { path: '/' });
            cookies.delete('role', { path: '/' });
            throw redirect(303, '/login');
        }

        const isLoggedIn = await response.json();
        if (!isLoggedIn.success) { 
            cookies.delete('bearer', { path: '/' });
            cookies.delete('username', { path: '/' });
            cookies.delete('role', { path: '/' });
            throw redirect(303, '/login');
        }
    } catch (error) {
        cookies.delete('bearer', { path: '/' });
        cookies.delete('username', { path: '/' });
        cookies.delete('role', { path: '/' });
        throw redirect(303, '/login');
    }

    try {
        const response = await fetch(BASE_URL_BACKEND + `/dishes/${params.id}`, {
            headers: {
                'Authorization': `Bearer ${bearer}`
            }
        });

        if (!response.ok) {
            throw new Error('Failed to fetch data');
        }

        const data = await response.json();
        const dish: Dish = data[0];

        console.log(dish)
        
        return { 
            dish,
            BASE_URL_BACKEND
        };

    } catch (error) {
        console.error(error);
        return { 
            error: fail(400, { error: error || 'Failed to add dish' }),
            dish: [], 
        };
    }
}