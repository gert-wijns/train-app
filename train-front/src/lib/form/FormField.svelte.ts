import { m } from "$lib/i18n/translate.svelte"

export type FormFieldValidator<V> = (formField: InputFormField<V>) => string | undefined
export type Model = Record<string, FormField<any>>

type ExtractModel<P> = P extends Model ? P : never
type ExtractFormFieldModel<P> = P extends FormField<infer T> ? T : never

type FormFieldType<F extends FormField<any> | object> = F extends FormField<any> ?
    ExtractFormFieldModel<F> :
    FormFields<ExtractModel<F>>;

export type FormFields<T extends Model> = {
    [KeyType in keyof T]: FormFieldType<T[KeyType]>;
}

export interface FormField<V> {
    hasErrors(): Promise<boolean>
    getId(): string
    setId(id: string): void
    toValue(): V
}

export class ObjectFormField<V extends Model> implements FormField<V> {
    private id = ""

    constructor(readonly model: V) { }

    getId() {
        return this.id
    }
    setId(id: string) {
        this.id = id
        Object.entries(this.model)
            .forEach(([name, field]) => field.setId(this.id + "." + name))
    }

    async hasErrors(): Promise<boolean> {
        return (await Promise.all(Object.values(this.model)
            .map(field => field.hasErrors())))
            .filter(hasError => hasError).length > 0
    }

    toValue() {
        const value = {} as any
        Object.entries(this.model)
            .forEach(([name, field]) => value[name] = field.toValue())
        return value
    }
}

export abstract class InputFormField<V> implements FormField<V> {
    private id = ""
    input = $state("")
    validators = $state<Array<FormFieldValidator<V>>>([])
    errors = $derived(this.validators
        .map(validator => validator(this))
        .filter(error => error !== undefined))

    getId() {
        return this.id
    }
    setId(id: string) {
        this.id = id
    }
    async hasErrors(): Promise<boolean> {
        return this.errors.length > 0
    }


    required(): InputFormField<NonNullable<V>> {
        this.validators.push(field => field.input.length === 0 ? m("VALIDATE_REQUIRED") : undefined)
        return this as any
    }

    abstract toValue(): V;
}

export class TextFormField<V extends string | undefined> extends InputFormField<V> {
    notBlank(): TextFormField<string> {
        this.validators.push(field => field.input.trim().length === 0 ? m("VALIDATE_NOT_BLANK") : undefined)
        return this as any
    }
    minLength(minLength: number): TextFormField<V> {
        this.validators.push(field => field.input?.length < minLength ? m("TEXT_TOO_SHORT", { minLength }) : undefined)
        return this
    }
    maxLength(maxLength: number): TextFormField<V> {
        this.validators.push(field => field.input?.length > maxLength ? m("TEXT_TOO_LONG", { maxLength }) : undefined)
        return this
    }
    toValue(): V {
        return this.input as any
    }
}

export class NumberFormField extends InputFormField<number | undefined> {
    toValue() {
        return +this.input
    }
}
