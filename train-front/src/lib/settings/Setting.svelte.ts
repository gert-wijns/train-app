import { browser } from '$app/environment';

export class Setting {
    key: string
    value?: string = $state()

    constructor(key: string) {
        this.key = "settings/" + key
        if (browser) {
            this.value = window.localStorage.getItem(this.key) || undefined
        }
    }

    
    clear = () => {
        window.localStorage.removeItem(this.key)
        this.value = undefined
    }

    set = (value: string) => {
        window.localStorage.setItem(this.key, value)
        this.value = value
    }

    get = () => {
        return this.value;
    }
}