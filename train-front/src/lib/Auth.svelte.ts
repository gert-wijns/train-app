import { browser } from "$app/environment"
import { invalidateAll } from "$app/navigation";
import { UserLoginUseCaseApi } from "$usermanagement-api/services/UserLoginUseCaseApi"

function parseJwt(token: string) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}

type User = {
    sub: string
    exp: number
    roles: string[]
}

export class Auth {
    private accessToken = $state<string>()
    private user = $derived(this.accessToken ? parseJwt(this.accessToken) : undefined)

    constructor() {
        if (browser) {
            this.accessToken = window.localStorage.getItem("token") || undefined
        }
    }

    isLoggedIn() {
        return this.accessToken !== undefined
    }

    async login(username: string, password: string) {
        const response = await UserLoginUseCaseApi.execute({
            requestBody: {
                username,
                password
            }
        })
        this.accessToken = response.token
        window.localStorage.setItem("token", this.accessToken)
    }

    logout() {
        this.accessToken = undefined
        window.localStorage.removeItem("token")
        invalidateAll()
    }

    getAccessToken() {
        return this.accessToken
    }

    getUserId() {
        return this.user.sub
    }

    hasRole(role: string) {
        return true;
    }
}

export const auth = new Auth()
