type User = {
    id: string
    firstName: string
}

export class Auth {
    private accessToken = $state(null as string | null)
    private user: User = {
        id:  'gert.wijns@gmail.com',
        firstName: 'Gert',
    }

    getAccessToken() {
        return this.accessToken
    }

    getUserId() {
        return this.user.id
    }

    getFirstName() {
        return this.user.firstName
    }

    hasRole(role: string) {
        return true;
    }
}

export const auth = new Auth()
