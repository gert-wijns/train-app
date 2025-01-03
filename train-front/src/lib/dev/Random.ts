

export function randomElement<T>(array: T[]): T | undefined {
    return array.length === 0 ? undefined : array[Math.floor(Math.random() * (array.length - .000001))]
}