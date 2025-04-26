export async function load() {
    try {
        const response = await fetch('http://localhost:8080/dishes');
        if (!response.ok) {
            throw new Error(`Failed to fetch dishes: ${response.statusText}`);
        }

        const dishes = await response.json();
        return {
            dishes
        };
    } catch (error) {
        console.error(error);
        return {
            error: 'Failed to load dishes'
        };
    }
}