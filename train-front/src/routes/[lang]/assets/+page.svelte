<script lang="ts">
  import { m } from '$lib/i18n/translate.svelte.js'
  import { type LoadData } from './+page.js'
  import Grid, { type Column } from '$lib/grid/Grid.svelte'
  import type { SearchAssetsQueryResponseItem } from '$assets-api/models/SearchAssetsQueryResponseItem.js'
  import { AssetType } from '$assets-api'
  import { route } from '$lib/ROUTES.js'
  import { onMount } from 'svelte'
  import { goto } from '$app/navigation'
  import TranslationCellRenderer from '$lib/grid/renderers/TranslationCellRenderer.svelte'

  let { data }: { data: LoadData } = $props()

  const addAssetHref = route('/[lang]/assets/add', { lang: 'en' })
  let columns: Column<SearchAssetsQueryResponseItem>[] = [
    {
      title: m('SERIAL_NUMBER'),
      get: row => row.serialNumber,
    },
    {
      title: m('TYPE'),
      get: row => row.type,
      component: TranslationCellRenderer,
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

  onMount(() => {
    const listener = (event: KeyboardEvent) => {
      if (event.key === 'a') {
        goto(addAssetHref)
      }
    }
    document.addEventListener('keypress', listener)
    return () => document.removeEventListener('keypress', listener)
  })
</script>

{#snippet assetType(value: AssetType)}
  <div class="overflow-hidden">{m(value as any)}</div>
{/snippet}
{#snippet assetSubType(value: string)}
  <div class="overflow-hidden">{value}</div>
{/snippet}

<div class="grid gap-5">
  <div class="text-2xl font-bold">{m('ASSETS')}</div>
  <div class="max-h-[calc(100vh-250px)]"><Grid {columns} rows={data.assets} /></div>
  <div class="grid">
    <a href={addAssetHref} class="ml-auto btn btn-primary">{m('ADD_ASSET')}</a>
  </div>
</div>
