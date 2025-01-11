<script lang="ts">
  import { invalidateAll } from '$app/navigation'
  import { page } from '$app/state'
  import { DecommissionWagonUseCaseApi } from '$assets-api/services/DecommissionWagonUseCaseApi'
  import { TextFormField } from '$lib/form/FormField.svelte'
  import Input from '$lib/form/Input.svelte'
  import Label from '$lib/form/Label.svelte'
  import type { Column } from '$lib/grid/Grid.svelte'
  import Grid from '$lib/grid/Grid.svelte'
  import { M, m } from '$lib/i18n/translate.svelte'
  import type { SearchTrainEngineersDetailQueryCertificationItemResponse } from '$planning-api/models/SearchTrainEngineersDetailQueryCertificationItemResponse'
  import type { LoadData } from './+page'

  let { data }: { data: LoadData } = $props()
  let details = $derived(data.details)

  let badgeNumber = new TextFormField()
  $effect(() => {
    badgeNumber.input = details.id
  })

  let columns: Column<SearchTrainEngineersDetailQueryCertificationItemResponse>[] = [
    {
      title: m('CERTIFICATE_CODE'),
      get: row => row.certificateCode,
    },
    {
      title: m('VALID_FROM'),
      get: row => row.certificationPeriod.start,
    },
    {
      title: m('VALID_TILL'),
      get: row => row.certificationPeriod.end,
    },
  ]
</script>

<div class="grid gap-5 grid-cols-[min-content_1fr] max-w-96 whitespace-nowrap">
  <span class="text-2xl font-bold col-span-2 flex gap-4 items-center">
    {m('TRAIN_ENGINEER')}
    {#if !details.active}
      <span class="badge badge-sm badge-error">
        {M('INACTIVE')}
      </span>
    {/if}
  </span>
  <Label formField={badgeNumber}>{M('BADGE_NR')}</Label>
  <Input formField={badgeNumber} disabled />
  <div class="col-span-2 max-h-[calc(100vh-250px)]">
    <Grid {columns} rows={details.certifications} />
  </div>
</div>
