const BACKEND_URL = "http://localhost:8080";

export async function load({ cookies }) {
    try {
        const username = cookies.get('username');
        const response = await fetch(BACKEND_URL + '/dishes');
        if (!response.ok) {
            throw new Error(`Failed to fetch dishes: ${response.statusText}`);
        }

        const dishes = await response.json();
        return {
            dishes,
            username
        };
    } catch (error) {
        console.error(error);
        return {
            error: 'Failed to load dishes'
        };
    }
}