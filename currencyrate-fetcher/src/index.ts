/**
 * Welcome to Cloudflare Workers! This is your first worker.
 *
 * - Run `npm run dev` in your terminal to start a development server
 * - Open a browser tab at http://localhost:8787/ to see your worker in action
 * - Run `npm run deploy` to publish your worker
 *
 * Bind resources to your worker in `wrangler.jsonc`. After adding bindings, a type definition for the
 * `Env` object can be regenerated with `npm run cf-typegen`.
 *
 * Learn more at https://developers.cloudflare.com/workers/
 */

import { fetchExchangerate } from "./handlers/currencyHandler";


export interface Env {
	API_DATA: KVNamespace,
	SWAP_API_KEY: string
}
export default {
	async fetch(request, env, ctx): Promise<Response> {
		try {
			
			const exchangerates = await env.API_DATA.get("rates")
			if (exchangerates === null) {
				return new Response("Value not found", {status: 404})
			}
			return new Response(exchangerates)
		}
		catch(err) {
			console.error(`KV return error:`, err)
			const errorMsg =
			err instanceof Error
				? err.message
				: "An unknown error occurred when accessing KV storage"
			return new Response(errorMsg, {
				status: 500,
				headers: {"Content-Type":"application/json"}
			})
		}
	},
	async scheduled(controller, env, ctx) {
		const data = await fetchExchangerate(env.SWAP_API_KEY)
		console.log(data)
		const jsonData = JSON.stringify(data)
		await env.API_DATA.put("rates", jsonData)
	}
} satisfies ExportedHandler<Env>;




