import { redirect } from '@sveltejs/kit';
import { env } from '$env/dynamic/private';
const BASE_URL_BACKEND = env.BASE_URL_BACKEND;
export async function checkLogin( cookies: { get?: any, delete?: any }) {
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
}
