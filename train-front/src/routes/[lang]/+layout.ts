
import { auth } from "$lib/Auth.svelte";
import { route as routes } from "$lib/ROUTES";
import { redirect } from "@sveltejs/kit"

export const load = async ({ params, route }) => {
    if (!auth.isLoggedIn() && route.id !== "/[lang]/login") {
        throw redirect(302, routes("/[lang]/login", { lang: params.lang }))
        //await auth.login()
    }
    //throw redirect(302, "/en")
}