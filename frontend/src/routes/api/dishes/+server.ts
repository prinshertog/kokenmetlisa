import type { RequestHandler } from './$types';
import { env } from '$env/dynamic/public';
const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;

export const GET: RequestHandler = async ({ url, fetch }) => {
  const page = url.searchParams.get('page') ?? '0';

  const res = await fetch(`${BASE_URL_BACKEND}/dishes?page=${page}`);

  return new Response(res.body, {
    status: res.status,
    headers: { 'content-type': 'application/json' }
  });
};