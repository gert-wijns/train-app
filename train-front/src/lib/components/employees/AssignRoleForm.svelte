<script lang="ts">
  import { registerDevAutofill } from '$lib/dev/DevAutofillHook.svelte'
  import { randomElement } from '$lib/dev/Random'
  import Form from '$lib/form/Form.svelte'
  import { TextFormField } from '$lib/form/FormField.svelte'
  import { FormModel } from '$lib/form/FormModel.svelte'
  import { m } from '$lib/i18n/translate.svelte'
  import { EmployeeRole } from '$personnel-api/models/EmployeeRole'
  import { onMount } from 'svelte'

  let { role, submit }: { role: EmployeeRole; submit: (role: EmployeeRole) => Promise<boolean> } = $props()

  let options = Object.values(EmployeeRole)
    .map(role => ({ label: m(role as any), value: role }))
    .sort((o1, o2) => o1.label.toLowerCase().localeCompare(o2.label.toLowerCase()))

  const selected = new TextFormField().required()
  const formModel = new FormModel(
    'AssignRoleForm',
    { selected },
    {
      submit: () => submit(selected.input as EmployeeRole),
    },
  )

  selected.input = role

  onMount(() =>
    registerDevAutofill(() => {
      selected.input = randomElement(options)?.value ?? EmployeeRole.UNASSIGNED
    }),
  )
</script>

<Form {formModel} class="grid gap-4">
  <div class="text-lg font-bold">{m('ASSIGN_ROLE')}</div>

  <div class="grid gap-1">
    {#each options as { label, value }}
      <label class="flex items-center gap-1">
        <input
          type="radio"
          name="AssignRoleForm-radio"
          class="radio radio-xs"
          checked={selected.input === value}
          onchange={_ => (selected.input = value)} />
        {label}
      </label>
    {/each}
  </div>

  <button type="submit" class="btn btn-primary ml-auto">
    {m('ASSIGN')}
  </button>
</Form>
