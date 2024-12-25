import { beforeNavigate } from "$app/navigation";


export function registerDirtyEffect(isDirty: () => boolean) {
    return () => {
        const listener = (event: BeforeUnloadEvent) => {
            if (isDirty()) {
                event.preventDefault();
                event.returnValue = '';
            }
        }
        window.addEventListener('beforeunload', listener)
        return () => {
            window.removeEventListener('beforeunload', listener)
        }
    }
}

export function registerConfirmBeforeNavigate(isDirty: () => boolean) {
    beforeNavigate(({cancel}) => {
        if (isDirty() && !confirm('Are you sure you want to leave this page? You have unsaved changes that will be lost.')) {
            cancel();
        }
    })
}

export function preventIfDirtyListener(isDirty: () => boolean) {
    return (e: MouseEvent) => {
        if (isDirty()) {
            e.preventDefault()
        } 
    }
}