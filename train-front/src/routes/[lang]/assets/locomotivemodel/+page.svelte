<script lang="ts">
  import TranslationCellRenderer from '$lib/grid/renderers/TranslationCellRenderer.svelte'
  import type { SearchLocomotiveModelsQueryResponseItem } from '$assets-api/models/SearchLocomotiveModelsQueryResponseItem.js'
  import Grid, { type Column } from '$lib/grid/Grid.svelte'
  import { m } from '$lib/i18n/translate.svelte.js'
  import { type LoadData } from './+page.js'

  let { data }: { data: LoadData } = $props()

  let columns: Column<SearchLocomotiveModelsQueryResponseItem>[] = [
    {
      title: m('NAME'),
      get: row => row.name,
    },
    {
      title: m('POWER_TYPE'),
      get: row => 'POWER_TYPE_' + row.powerType,
      component: TranslationCellRenderer,
    },
    {
      title: m('GAUGE'),
      get: row => row.gauge,
    },
  ]
</script>

<div class="grid gap-5">
  <div class="text-2xl font-bold">{m('LOCOMOTIVE_MODELS')}</div>
  <div class="max-h-[calc(100vh-250px)]"><Grid {columns} rows={data.models} /></div>
</div>
