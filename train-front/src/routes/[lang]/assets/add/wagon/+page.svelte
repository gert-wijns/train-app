<script lang="ts">
  import { goto } from '$app/navigation'
  import { page } from '$app/state'
  import { AddWagonUseCaseApi } from '$assets-api/services/AddWagonUseCaseApi'
  import WagonModelTypeInput from '$lib/components/assets/WagonModelTypeInput.svelte'
  import { registerDevAutofill } from '$lib/dev/DevAutofillHook.svelte'
  import { randomElement } from '$lib/dev/Random'
  import { dayOfYearMinutesOfDayAndSeconds } from '$lib/dev/StringGenerator'
  import Form from '$lib/form/Form.svelte'
  import { TextFormField } from '$lib/form/FormField.svelte'
  import { FormModel } from '$lib/form/FormModel.svelte'
  import Input from '$lib/form/Input.svelte'
  import Label from '$lib/form/Label.svelte'
  import { m } from '$lib/i18n/translate.svelte'
  import { route } from '$lib/ROUTES'
  import { onMount } from 'svelte'
  import { v4 as uuidv4 } from 'uuid'

  const wagonId = uuidv4()

  const modelTypeId = new TextFormField().notBlank()
  const serialNumber = new TextFormField().notBlank()
  const gauge = new TextFormField().notBlank()
  const formModel = new FormModel(
    'AddWagonConfiguraAssetForm',
    {
      modelTypeId,
      serialNumber,
    },
    { submit },
  )

  async function submit() {
    await AddWagonUseCaseApi.execute({ requestBody: { wagonId, ...formModel.toValue() } })
    goto(route('/[lang]/assets', { lang: page.params.lang }))
    return true
  }

  let wagonModelTypeInput: WagonModelTypeInput
  onMount(() =>
    registerDevAutofill(() => {
      serialNumber.input = dayOfYearMinutesOfDayAndSeconds()
      modelTypeId.input = randomElement(wagonModelTypeInput.getOptions())?.value ?? ''
      modelTypeId.focus()
    }),
  )
</script>

<Form {formModel} class="grid gap-2 grid-cols-[min-content_1fr] min-w-lg items-center">
  <div class="text-lg font-bold col-span-2">{m('WAGON')}</div>
  <Label formField={serialNumber}>{m('SERIAL_NUMBER')}</Label>
  <Input formField={serialNumber} autocomplete="off" />
  <Label formField={modelTypeId}>{m('MODEL_TYPE')}</Label>
  <WagonModelTypeInput bind:this={wagonModelTypeInput} formField={modelTypeId} />
  <button type="submit" class="btn btn-primary ml-auto col-span-2">
    {m('SAVE')}
  </button>
</Form>
