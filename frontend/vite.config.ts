import { defineConfig } from 'vite';
import tailwindcss from '@tailwindcss/vite';
import { sveltekit } from '@sveltejs/kit/vite';
import dotenv from 'dotenv';

dotenv.config();

export default defineConfig({
	plugins: [
		tailwindcss(),
		sveltekit(),
	],
	server: {
		host: true,
		port: 3003,
		strictPort: true
	},
	preview: {
		host: true,
		port: 3003,
		strictPort: true
	},
	define: {
		'process.env.BASE_URL_BACKEND': JSON.stringify(process.env.BASE_URL_BACKEND),
		'process.env.BODY_SIZE_LIMIT': JSON.stringify(process.env.BODY_SIZE_LIMIT)
	}
});