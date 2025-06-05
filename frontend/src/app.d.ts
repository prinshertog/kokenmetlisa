// See https://svelte.dev/docs/kit/types#app.d.ts
// for information about these interfaces
declare global {
	namespace App {
		// interface Error {}
		// interface Locals {}
		// interface PageData {}
		// interface Platform {}
	}
	namespace NodeJS {
		interface ProcessEnv {
			BASE_URL_BACKEND: string;
		}
	}
}

export {};
