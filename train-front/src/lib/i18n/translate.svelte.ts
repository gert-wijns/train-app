import * as en from './en.json'

export function setLanguage(lang: string) {
    switch (lang) {
        case 'en': translations = en as Record<string, string>; break;
    }
}

let translations = en as Record<string, string>;

export const m = (key: keyof typeof en, args?: Record<string, unknown>) => {
    if (translations[key]) {
        let translation = translations[key]
        if (args) {
            Object.entries(args).forEach(([paramKey, paramValue]) => {
                translation = translation.replace("${" + paramKey + "}", paramValue as string)
            })
        }
        return translation
    }
    return key
}

export const M = (key: keyof typeof en, args?: Record<string, unknown>) => {
    return m(key, args).toUpperCase()
}