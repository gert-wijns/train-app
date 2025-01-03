import { ObjectFormField, type FormField, type FormFields, type Model } from "./FormField.svelte";

export class FormModel<M extends Model> {
    public model: ObjectFormField<M>;
    private readonly submit: () => Promise<boolean>
    public error = $state<string>()

    constructor(id: string, model: M, { submit }: { submit: () => Promise<boolean> }) {
        this.model = new ObjectFormField(model)
        this.model.setId(id)
        this.submit = submit
    }

    public async onSubmit(): Promise<boolean> {
        this.error = undefined
        if (await this.model.hasErrors()) {
            this.error = "Form has unresolved errors"
            return false;
        }
        try {
            return await this.submit()
        } catch (e: unknown) {
            this.error = "" + e
            return false;
        }
    }

    toValue(): FormFields<M> {
        return this.model.toValue()
    }
}
