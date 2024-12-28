import { sveltekit } from '@sveltejs/kit/vite'
import type { KIT_ROUTES } from "$lib/ROUTES"
import { kitRoutes } from 'vite-plugin-kit-routes'
import { defineConfig } from 'vite';
import path from "path";
import tailwindcss from "@tailwindcss/vite";

export default defineConfig({
	plugins: [sveltekit(), tailwindcss(), kitRoutes<KIT_ROUTES>({
		PAGES: {
		}
	})],
	server: {
		proxy: {
			'/api': {
				target: 'http://localhost:8080',
				changeOrigin: true,
				configure: (proxy, _options) => {
					proxy.on('error', (err, _req, _res) => {
						console.log('proxy error', err);
					});
					proxy.on('proxyReq', (proxyReq, req, _res) => {
						console.log('Sending Request to the Target:', req.method, req.url);
					});
					proxy.on('proxyRes', (proxyRes, req, _res) => {
						console.log('Received Response from the Target:', proxyRes.statusCode, req.url);
					});
				},
			}
		}
	},
	resolve: {
		alias: {
			"$lib": `${path.resolve(__dirname, "./src/lib")}`,
			"$assets-api": `${path.resolve(__dirname, "./src/generated/service-api/assets")}`,
			"$planning-api": `${path.resolve(__dirname, "./src/generated/service-api/planning")}`,
			"$personnel-api": `${path.resolve(__dirname, "./src/generated/service-api/personnel")}`,
			"$network-api": `${path.resolve(__dirname, "./src/generated/service-api/network")}`
		}
	}
});
