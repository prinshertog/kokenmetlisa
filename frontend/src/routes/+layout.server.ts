export async function load() {
    try {
        const [dishesResponse, categoriesResponse] = await Promise.all([
            fetch('http://localhost:8080/dishes'),
            fetch('http://localhost:8080/category')
        ]);

        if (!dishesResponse.ok || !categoriesResponse.ok) {
            throw new Error('Failed to fetch data');
        }

        const [dishes, categories] = await Promise.all([
            dishesResponse.json(),
            categoriesResponse.json()
        ]);

        return { dishes, categories };
    } catch (error) {
        console.error(error);
        return {
            error: 'Failed to load data'
        };
    }
  }