<script lang="ts">
  import { m } from '$lib/i18n/translate.svelte.js'
  import { type LoadData } from './+page.js'
  import Grid, { type Column } from '$lib/grid/Grid.svelte'
  import type { SearchAssetsQueryResponseItem } from '$assets-api/models/SearchAssetsQueryResponseItem.js'
  import { AssetType } from '$assets-api'
  import { route } from '$lib/ROUTES.js'

  let { data }: { data: LoadData } = $props()

  let columns: Column<SearchAssetsQueryResponseItem>[] = [
    {
      title: m('TYPE'),
      get: row => row.type,
      snippet: assetType,
    },
    {
      title: m('SUB_TYPE'),
      get: row => row.subtype,
      snippet: assetSubType,
    },
    {
      title: m('NAME'),
      get: row => row.name,
    },
  ]
</script>

{#snippet assetType(value: AssetType)}
  <div class="overflow-hidden">{m(value as any)}</div>
{/snippet}
{#snippet assetSubType(value: string)}
  <div class="overflow-hidden">{value}</div>
{/snippet}

<div class="grid gap-5">
  <div class="text-2xl font-bold">{m('ASSETS')}</div>
  <div class="max-h-[300px]"><Grid {columns} rows={data.assets} /></div>
  <div class="grid">
    <a href={route('/[lang]/assets/add', { lang: 'en' })} class="ml-auto btn btn-primary">{m('ADD_ASSET')}</a>
  </div>
</div>
