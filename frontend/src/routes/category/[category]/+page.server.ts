import { error } from '@sveltejs/kit';
const BASE_URL_BACKEND = import.meta.env.VITE_BASE_URL_BACKEND;

interface Category {
    category: string,
    parentCategory: Category | null
}

interface Dish {
    id: number,
    name: string,
    description: string,
    category: Category
    imageName: string
}

export async function load({ params, fetch }) {
    try {
        const response = await fetch(`${BASE_URL_BACKEND}/dishes`);
        if (!response.ok) {
            const errorData = await response.text();
            throw error(response.status, errorData);
        }
        const data: Dish[] = await response.json();
        if (data.length === 0) {
            throw error(404, "No dishes found");
        }
        const categoryDishes = data.filter(dish => 
            dish.category.category === params.category || dish.category.parentCategory?.category === params.category
        )
        if (categoryDishes.length === 0) {
            throw error(404, "No dishes found");
        }
        return { categoryDishes, BASE_URL_BACKEND };

    } catch (err: any) {
        if (err.status && err.body) {
            throw err;
        }
        throw error(500, "Failed to fetch dishes");
    }
}