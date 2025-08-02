import { redirect, fail } from "@sveltejs/kit";
import type { Actions, PageServerLoad } from "./$types";
import { env } from '$env/dynamic/private';
const BASE_URL_BACKEND = env.BASE_URL_BACKEND;

export const actions = {
    logout: async ({ cookies }) => {
        cookies.delete('bearer', { path: '/' });
        cookies.delete('username', { path: '/' });
        cookies.delete('role', { path: '/' });
        throw redirect(303, '/login');
    },
    login: async ({ cookies, request }) => {
        try {
            const data = await request.formData();
            const username = data.get('username');
            const password = data.get('password');
            const userData = {
                username: username,
                password: password
            }
            const response = await fetch(BASE_URL_BACKEND + '/login', {
                method: "POST",
                headers: { "Content-Type": "application/json"},
                body: JSON.stringify(userData)
            });

            if (!response.ok) {
                const errorText = await response.json();
                return fail(400, { error: errorText.error || 'Login failed' });
            }

            const authData = await response.json();
            
            if (typeof username === 'string') {
                cookies.set("username", username, {path: "/"});
                cookies.set("bearer", authData.bearerToken, {
                    path: "/",
                    httpOnly: true,
                    secure: process.env.NODE_ENV === 'production',
                    sameSite: 'strict'
                });
                cookies.set("role", authData.role, {path: "/"});
            }

            throw redirect(303, "/dashboard");
        } catch (error) {
            if (error instanceof redirect) {
                throw error;
            }
            return fail(500, { error: 'Login failed. Please try again.' + error });
        }
    }
} satisfies Actions;

export const load: PageServerLoad = async ({ cookies }) => {
    const bearer = cookies.get('bearer');
    if (bearer) {
        throw redirect(303, '/dashboard');
    }
    return {};
};