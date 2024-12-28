import { v4 as uuidv4 } from "uuid";

export type ToastParams = {
    id: string
    className: string
    message: string
}

export let toasts: Array<ToastParams> = $state([])

export function pushToast(message: string, className: string, duration?: number) {
    const id = uuidv4()
    toasts.push({ id, message, className })
    if (duration !== undefined) {
        setTimeout(() => {
            const idx = toasts.findIndex(t => t.id === id)
            if (idx >= 0) {
                toasts.splice(idx, 1)
            }
        }, duration)
    }
} 