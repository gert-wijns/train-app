<script lang="ts">
  import { m } from '$lib/i18n/translate.svelte.js'
  import { type LoadData } from './+page.js'
  import Grid, { type Column } from '$lib/grid/Grid.svelte'
  import type { SearchAssetsQueryResponseItem } from '$assets-api/models/SearchAssetsQueryResponseItem.js'
  import { route } from '$lib/ROUTES.js'
  import { goto } from '$app/navigation'
  import TranslationCellRenderer from '$lib/grid/renderers/TranslationCellRenderer.svelte'
  import { createKeydownListener } from '$lib/common/keyListener.js'
  import { onMount } from 'svelte'
  import { AssetType } from '$assets-api/models/AssetType.js'
  import { page } from '$app/state'

  let { data }: { data: LoadData } = $props()

  const addAssetHref = route('/[lang]/assets/add', { lang: page.params.lang })
  let columns: Column<SearchAssetsQueryResponseItem>[] = [
    {
      title: m('SERIAL_NUMBER'),
      get: row => row.serialNumber,
      snippet: detailLinks,
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

  onMount(createKeydownListener('a', () => goto(addAssetHref)))
</script>

{#snippet detailLinks(value: string, row: SearchAssetsQueryResponseItem)}
  {#if row.type === AssetType.LOCOMOTIVE}
    <a
      class="link"
      href={route('/[lang]/assets/locomotives/[locomotiveId]', { lang: page.params.lang, locomotiveId: row.id })}>
      {value}
    </a>
  {:else if row.type === AssetType.WAGON}
    <a class="link" href={route('/[lang]/assets/wagons/[wagonId]', { lang: page.params.lang, wagonId: row.id })}>
      {value}
    </a>
  {:else}
    {value}
  {/if}
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
