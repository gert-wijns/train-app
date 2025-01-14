
import { auth } from "$lib/Auth.svelte.js"
import { error } from "@sveltejs/kit"

export const load = async () => {
    if (!auth.hasRole('PERSONNEL_ADMIN')) {
        error(403, { message: 'insufficient_access', role: 'PERSONNEL_ADMIN' } as any)
    }
    return {}
}