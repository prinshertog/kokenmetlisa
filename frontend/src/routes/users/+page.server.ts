import { fail, redirect } from '@sveltejs/kit';
import type { Actions, PageServerLoad } from './$types';
import { env } from '$env/dynamic/private';
import { checkLogin } from '$lib/methods/loginCheck';
const BASE_URL_BACKEND = env.BASE_URL_BACKEND;


export const load: PageServerLoad = async ({ cookies }) => {
    const bearer = cookies.get('bearer');
    const role = cookies.get('role');
    const username = cookies.get('username');

    await checkLogin(cookies);

    if (role !== 'ADMIN' && role !== 'USER') {
        throw redirect(303, '/dashboard');
    }

    let users = [];
    if (role === 'ADMIN') {
        try {
            const response = await fetch(`${BASE_URL_BACKEND}/users`, {
                headers: {
                    'Authorization': `Bearer ${bearer}`,
                    'Content-Type': 'application/json'
                }
            });
            
            if (response.ok) {
                users = await response.json();
            }
        } catch (error) {
            console.error('Failed to fetch users:', error);
        }
    }

    return {
        users,
        isAdmin: role === 'ADMIN',
        currentUser: username,
        role
    };
};

export const actions = {
    logout: async ({ cookies }) => {
        cookies.delete('bearer', { path: '/' });
        cookies.delete('username', { path: '/' });
        cookies.delete('role', { path: '/' });
        throw redirect(303, '/login');
    },
    createUser: async ({ request, cookies }) => {
        try {
            const data = await request.formData();
            const userData = {
                username: data.get('username'),
                password: data.get('password'),
                role: data.get('role')
            };

            const response = await fetch(`${BASE_URL_BACKEND}/users`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${cookies.get('bearer')}`
                },
                body: JSON.stringify(userData)
            });

            if (!response.ok) {
                const error = await response.json();
                return fail(400, { error: error.error || 'Failed to create user' });
            }

            return { success: true, message: 'User created successfully' };
        } catch (error) {
            return fail(500, { error: 'Failed to create user' });
        }
    },

    deleteUser: async ({ request, cookies }) => {
        try {
            const data = await request.formData();
            const username = data.get('username');

            const response = await fetch(`${BASE_URL_BACKEND}/users`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${cookies.get('bearer')}`
                },
                body: username
            });

            if (!response.ok) {
                const error = await response.json();
                return fail(400, { error: error.error || 'Failed to delete user' });
            }

            return { success: true, message: 'User deleted successfully' };
        } catch (error) {
            return fail(500, { error: 'Failed to delete user' });
        }
    },

    changePassword: async ({ request, cookies }) => {
        try {
            const data = await request.formData();
            const passwordData = {
                username: data.get('username'),
                oldPassword: data.get('oldPassword'),
                newPassword: data.get('newPassword')
            };

            const response = await fetch(`${BASE_URL_BACKEND}/users/password`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${cookies.get('bearer')}`
                },
                body: JSON.stringify(passwordData)
            });

            if (!response.ok) {
                const error = await response.json();
                return fail(400, { error: error.error || 'Failed to change password' });
            }

            return { success: true, message: 'Password changed successfully' };
        } catch (error) {
            return fail(500, { error: 'Failed to change password' });
        }
    }
} satisfies Actions;
