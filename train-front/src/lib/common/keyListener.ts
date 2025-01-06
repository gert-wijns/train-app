export function createKeydownListener(key: string | string[], keyListener: (event: KeyboardEvent) => void) {
    return createKeyListener('keydown', key, keyListener)
}

export function createKeypressListener(key: string | string[], keyListener: (event: KeyboardEvent) => void) {
    return createKeyListener('keypress', key, keyListener)
}

function createKeyListener(event: 'keypress' | 'keydown' | 'keyup', keyOrKeys: string | string[], keyListener: (event: KeyboardEvent) => void) {
    return () => {
        let keys = Array.isArray(keyOrKeys) ? keyOrKeys : [keyOrKeys]
        let filters = keys.map(keyWithModifiers => {
            let key = keyWithModifiers
            const altKey = key.includes('alt+')
            if (altKey) {
                key = key.replace('alt+', '')
            }
            const ctrlKey = key.includes('ctrl+')
            if (ctrlKey) {
                key = key.replace('ctrl+', '')
            }
            const shiftKey = key.includes('shift+')
            if (shiftKey) {
                key = key.replace('shift+', '')
            }
            return {
                key, altKey, ctrlKey, shiftKey
            }
        })

        const listener = (event: KeyboardEvent) => {
            for (const { key, altKey, ctrlKey, shiftKey } of filters) {
                if (event.key === key
                    && event.altKey === altKey
                    && event.ctrlKey === ctrlKey
                    && event.shiftKey === shiftKey) {
                    keyListener(event)
                    break;
                }
            }
        }
        document.addEventListener(event, listener)
        return () => document.removeEventListener(event, listener)
    }
}