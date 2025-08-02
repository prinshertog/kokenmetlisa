import { env } from '$env/dynamic/private';
const BASE_URL_BACKEND = env.BASE_URL_BACKEND;
export async function load() {
    try {
        const categoriesResponse = await fetch(BASE_URL_BACKEND + "/category");

        if (!categoriesResponse.ok) {
            throw new Error(`Failed to fetch data: ${categoriesResponse.statusText}`);
        }

        const categories = await categoriesResponse.json();
        return { categories };
    } catch (error) {
        console.error("Error fetching data:", error);
        return { error: "Failed to fetch categories" };
    }
}
