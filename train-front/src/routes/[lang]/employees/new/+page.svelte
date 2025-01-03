<script lang="ts">
  import { goto } from '$app/navigation'
  import { page } from '$app/state'
  import { registerDevAutofill } from '$lib/dev/DevAutofillHook.svelte'
  import { generatePersonName } from '$lib/dev/StringGenerator'
  import Form from '$lib/form/Form.svelte'
  import { ObjectFormField, TextFormField } from '$lib/form/FormField.svelte'
  import { FormModel } from '$lib/form/FormModel.svelte'
  import Input from '$lib/form/Input.svelte'
  import Label from '$lib/form/Label.svelte'
  import { m } from '$lib/i18n/translate.svelte'
  import { route } from '$lib/ROUTES'
  import { NewEmployeeUseCaseApi } from '$personnel-api/services/NewEmployeeUseCaseApi'
  import { onMount } from 'svelte'
  import { v4 as uuidv4 } from 'uuid'

  const employeeId = uuidv4()

  const firstName = new TextFormField().notBlank().minLength(3)
  const lastName = new TextFormField().notBlank()
  const formModel = new FormModel(
    'NewEmployeeForm',
    {
      fullName: new ObjectFormField({ firstName, lastName }),
    },
    { submit },
  )

  async function submit() {
    await NewEmployeeUseCaseApi.execute({ requestBody: { employeeId, ...formModel.toValue() } })
    goto(route('/[lang]/employees/new/[employeeId]/assign-role', { lang: page.params.lang, employeeId }))
    return true
  }

  onMount(() =>
    registerDevAutofill(() => {
      firstName.input = generatePersonName()
      lastName.input = generatePersonName()
    }),
  )
</script>

<Form {formModel} class="grid gap-2 grid-cols-[min-content_1fr] min-w-lg items-center">
  <div class="text-lg font-bold col-span-2">{m('ENTER_BIO')}</div>
  <Label formField={firstName}>{m('FIRST_NAME')}</Label>
  <Input formField={firstName} autocomplete="off" />
  <Label formField={lastName}>{m('LAST_NAME')}</Label>
  <Input formField={lastName} autocomplete="off" />
  <button type="submit" class="btn btn-primary ml-auto col-span-2">
    {m('SAVE')}
  </button>
</Form>
