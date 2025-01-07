<script lang="ts">
  import { type SearchWagonModelsQueryResponseItem } from '$assets-api/models/SearchWagonModelsQueryResponseItem.js'
  import Grid, { type Column } from '$lib/grid/Grid.svelte'
  import TranslationCellRenderer from '$lib/grid/renderers/TranslationCellRenderer.svelte'
  import { m } from '$lib/i18n/translate.svelte.js'
  import { type LoadData } from './+page.js'

  let { data }: { data: LoadData } = $props()

  let columns: Column<SearchWagonModelsQueryResponseItem>[] = [
    {
      title: m('NAME'),
      get: row => row.name,
    },
    {
      title: m('GAUGE'),
      get: row => row.gauge,
    },
    {
      title: m('TYPE'),
      get: row => 'WAGON_TYPE_' + row.type,
      component: TranslationCellRenderer,
    },
  ]
</script>

<div class="grid gap-5">
  <div class="text-2xl font-bold">{m('WAGON_MODELS')}</div>
  <div class="max-h-[calc(100vh-250px)]"><Grid {columns} rows={data.models} /></div>
</div>
