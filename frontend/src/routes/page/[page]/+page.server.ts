import { env } from '$env/dynamic/public';
const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;
export const load = async ({ params, fetch, setHeaders }) => {
    const pageNumber = Number(params.page);

    setHeaders({
        'cache-control': 'no-store'
    });

    const response = await fetch(
        `${BASE_URL_BACKEND}/dishes/page/${pageNumber}`
    );

    const pageObject = await response.json();

    return {
        pageObject,
        BASE_URL_BACKEND
    };
};