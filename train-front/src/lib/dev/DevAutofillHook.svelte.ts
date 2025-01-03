import { dev } from '$app/environment'
import { v4 as uuidv4 } from 'uuid'

type AutoFillerEntry = {
    id: string
    autoFiller: () => void
}
let registered: AutoFillerEntry[] = $state([])

export function registerDevAutofill(autoFiller: () => void) {
    if (!dev) {
        return undefined
    }
    const newEntry = { id: uuidv4(), autoFiller }
    registered.push(newEntry)
    return () => {
        registered.splice(registered.findIndex(entry => entry.id !== newEntry.id), 1)
    }
}

export function applyRegisteredDevAutoFills() {
    registered.forEach(entry => entry.autoFiller())
}