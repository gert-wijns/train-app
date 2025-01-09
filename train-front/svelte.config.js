import adapter from '@sveltejs/adapter-node';
import { vitePreprocess } from '@sveltejs/vite-plugin-svelte';

/** @type {import('@sveltejs/kit').Config} */
const config = {
	// Consult https://kit.svelte.dev/docs/integrations#preprocessors
	// for more information about preprocessors
	preprocess: vitePreprocess(),

	kit: {
		// adapter-auto only supports some environments, see https://kit.svelte.dev/docs/adapter-auto for a list.
		// If your environment is not supported or you settled on a specific environment, switch out the adapter.
		// See https://kit.svelte.dev/docs/adapters for more information about adapters.
		adapter: adapter(),
		alias: {
			"$lib": "src/lib/",
			"$lib/*": "src/lib/*",
			"$assets-api": "./src/generated/service-api/assets/",
			"$assets-api/*": "./src/generated/service-api/assets/*",
			"$planning-api": "./src/generated/service-api/planning/",
			"$planning-api/*": "./src/generated/service-api/planning/*",
			"$personnel-api": "./src/generated/service-api/personnel/",
			"$personnel-api/*": "./src/generated/service-api/personnel/*",
			"$network-api": "./src/generated/service-api/network/",
			"$network-api/*": "./src/generated/service-api/network/*",
			"$usermanagement-api": "./src/generated/service-api/usermanagement/",
			"$usermanagement-api/*": "./src/generated/service-api/usermanagement/*"
		}
	}
};

export default config;
