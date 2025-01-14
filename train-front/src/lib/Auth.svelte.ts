import { browser } from "$app/environment"
import { invalidateAll } from "$app/navigation";
import { UserLoginUseCaseApi } from "$usermanagement-api/services/UserLoginUseCaseApi"

function parseUser(token: string): User {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload) as User;
}

type User = {
    sub: string
    exp: number
    roles: string[]
}

export class Auth {
    private accessToken = $state<string>()
    private user = $state<User>()

    constructor() {
        if (browser) {
            this.accessToken = window.localStorage.getItem("token") || undefined
            this.user = this.accessToken ? parseUser(this.accessToken) : undefined
        }

        setInterval(() => {
            const exp = this.user?.exp
            if (exp && exp - 30 < new Date().getTime() / 1000) {
                this.logout()
            }
        }, 30000)
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
        this.user = parseUser(this.accessToken)
        window.localStorage.setItem("token", this.accessToken)
    }

    logout() {
        this.accessToken = undefined
        this.user = undefined
        window.localStorage.removeItem("token")
        invalidateAll()
    }

    getAccessToken() {
        return this.accessToken
    }

    getUserId() {
        return this.user?.sub
    }

    hasRole(role: string) {
        return this.user?.roles.includes(role);
    }
}

export const auth = new Auth()
