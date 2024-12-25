import { browser } from '$app/environment';

export class Toggle {
    key: string
    on = $state(false)

    constructor(key: string) {
        this.key = "settings/toggles/" + key
        if (browser && window.localStorage.getItem(this.key) === "true") {
            this.toggle()
        }
    }

    toggle = () => {
        this.on = !this.on
        window.localStorage.setItem(this.key, "" + this.on)
    }
    
    isOn = () => {
        return this.on
    }

    isOff = () => {
        return !this.on;
    }
}