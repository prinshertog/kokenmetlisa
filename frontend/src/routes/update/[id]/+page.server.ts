import { env } from '$env/dynamic/public';
const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;
import { fail } from '@sveltejs/kit';
import type { PageServerLoad, Actions } from './$types';
import { checkLogin } from '$lib/methods/loginCheck';

export const actions = {
    update: async ({ request, cookies, params, fetch }) => {
        try {
            const apiUrl = BASE_URL_BACKEND + `/dishes/${params.id}`;
            const data = await request.formData();
            let image = data.get('image') as File;

            let filename = `${Date.now()}-${image.name}`;

            const dishUpdateRequest = {
                id: params.id,
                dishName: data.get('name'),
                description: data.get('description'),
                category: data.get('category'),
                imageName: filename
            };

            const bearer = cookies.get('bearer');
            const formData = new FormData();
            formData.append("file", image);
            formData.append("dishUpdateRequest", new Blob([JSON.stringify(dishUpdateRequest)], { type: "application/json" }));

            const response = await fetch(apiUrl, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${bearer}`
                },
                body: formData
            });
            const receivedData = await response.json();
            if (!response.ok) {
                return fail(400, {error: receivedData.error || 'Failed to update dish'})   
            }
            if (!data || receivedData.length === 0) {
                return fail(400, {error: receivedData.error || 'Failed to update dish'})
            }
            return {success: true}
        } catch (error) {
            return fail(500, {
                error: error instanceof Error ? error.message : 'An unknown error occurred'
            });
        }
    }
} satisfies Actions

export const load: PageServerLoad = async ({ cookies, params }) => {
    const bearer = cookies.get('bearer');
    await checkLogin(cookies);

    try {
        const username = cookies.get('username');
        const response = await fetch(BASE_URL_BACKEND + `/dishes/${params.id}`, {
            headers: {
                'Authorization': `Bearer ${bearer}`
            }
        });

        if (!response.ok) {
            throw new Error('Failed to fetch data');
        }

        const data = await response.json();
        const dish = data[0];
        
        return { 
            dish,
            username
        };

    } catch (error) {
        return fail(500, {
            error: error instanceof Error ? error.message : 'An unknown error occurred'
        });
    }
}