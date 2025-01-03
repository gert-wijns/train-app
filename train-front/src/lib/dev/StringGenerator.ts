
import { uniqueNamesGenerator, adjectives, colors, animals, names } from 'unique-names-generator';

export function generatePersonName() {
    return uniqueNamesGenerator({ dictionaries: [names] });
}

export function generateName() {
    return uniqueNamesGenerator({ dictionaries: [adjectives, colors, animals] });
}

function generateFromCharacter(c: string) {
    switch (c) {
        case "#": return Math.floor(Math.random() * 9.9999)
        default: return c
    }
}

export function generateFromPattern(pattern: string): string {
    return [...pattern].map(c => generateFromCharacter(c)).join("")
}

export function dayOfYearMinutesOfDayAndSeconds(): string {
    const date = new Date()
    return ("" + daysIntoYear(date)).padStart(3, "0") + "-" +
        ("" + (date.getHours() * 60 + date.getMinutes())).padStart(4, "0") + "-" +
        ("" + date.getSeconds()).padStart(2, "0")
}

function daysIntoYear(date: Date) {
    return (Date.UTC(date.getFullYear(), date.getMonth(), date.getDate()) - Date.UTC(date.getFullYear(), 0, 0)) / 24 / 60 / 60 / 1000;
}