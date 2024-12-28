import type { ApiError } from "$assets-api";
import { ObjectFormField, type FormField, type Model } from "./FormField.svelte";

export function createFormModel<M extends Model>(id: string, model: M) {
    const formModel = new ObjectFormField(model)
    formModel.setId(id)
    return formModel
}

export class FormModel<M extends FormField<any>> {
    public model: M;
    private readonly submit: () => Promise<boolean>
    public error = $state<string>()

    constructor({ model, submit }: { model: M, submit: () => Promise<boolean> }) {
        this.model = model
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
}
