import type { RequestHandler } from './$types';
import { env } from '$env/dynamic/public';

const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;

export const GET: RequestHandler = async ({ params }) => {
    const apiUrl = BASE_URL_BACKEND + `/file/${params.categoryName}`;
    const res = await fetch(apiUrl);

    if (!res.ok) {
        return new Response('Not found', { status: 404 });
    }

    const blob = await res.blob();
    return new Response(blob, {
        headers: { 'Content-Type': res.headers.get('Content-Type') ?? 'image/png' }
    });
};