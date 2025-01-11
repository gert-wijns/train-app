<script lang="ts">
  import { invalidateAll } from '$app/navigation'
  import { page } from '$app/state'
  import { DecommissionLocomotiveUseCaseApi } from '$assets-api/services/DecommissionLocomotiveUseCaseApi'
  import { TextFormField } from '$lib/form/FormField.svelte'
  import Input from '$lib/form/Input.svelte'
  import Label from '$lib/form/Label.svelte'
  import { M, m } from '$lib/i18n/translate.svelte'
  import type { LoadData } from './+page'

  let { data }: { data: LoadData } = $props()
  let locomotive = $derived(data.locomotive)

  const modelTypeId = new TextFormField()
  const name = new TextFormField()
  const modelName = new TextFormField()
  const gauge = new TextFormField()
  const serialNumber = new TextFormField()

  $effect(() => {
    name.input = locomotive.name
    serialNumber.input = locomotive.serialNumber
    modelName.input = locomotive.model.name
    gauge.input = locomotive.model.gauge
    modelTypeId.input = locomotive.model.id
  })

  const decomission = async () => {
    await DecommissionLocomotiveUseCaseApi.execute({ requestBody: { id: page.params.locomotiveId } })
    invalidateAll()
  }
</script>

<div class="grid gap-5 grid-cols-[min-content_1fr] max-w-96 whitespace-nowrap">
  <span class="text-2xl font-bold col-span-2 flex gap-4 items-center">
    {m('LOCOMOTIVE')}
    {#if locomotive.decommissioned}
      <span class="badge badge-sm badge-error">
        {M('DECOMMISSIONED')}
      </span>
    {/if}
  </span>
  <Label formField={serialNumber}>{m('SERIAL_NUMBER')}</Label>
  <Input formField={serialNumber} disabled />
  <Label formField={name}>{m('NAME')}</Label>
  <Input formField={name} disabled />
  <Label formField={modelName}>{m('MODEL')}</Label>
  <Input formField={modelName} disabled />
  <Label formField={gauge}>{m('GAUGE')}</Label>
  <Input formField={gauge} disabled />
  <div class="col-span-2 ml-auto">
    {#if !locomotive.decommissioned}
      <button class="btn btn-outline" onclick={decomission}>
        {m('DECOMMISSION')}
      </button>
    {/if}
  </div>
</div>
