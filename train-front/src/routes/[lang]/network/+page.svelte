<script lang="ts">
  import { goto } from '$app/navigation'
  import { page } from '$app/state'
  import { createKeydownListener } from '$lib/common/keyListener.js'
  import Grid, { type Column } from '$lib/grid/Grid.svelte'
  import { M, m } from '$lib/i18n/translate.svelte.js'
  import { route } from '$lib/ROUTES.js'
  import { onMount } from 'svelte'
  import { type LoadData } from './+page.js'
  import type { SearchNetworkQueryResponseItem } from '$network-api/models/SearchNetworkQueryResponseItem.js'

  let { data }: { data: LoadData } = $props()

  let columns: Column<SearchNetworkQueryResponseItem>[] = [
    {
      title: m('NAME'),
      get: row => row.name,
      snippet: detailLinks,
    },
  ]
</script>

{#snippet detailLinks(value: string, row: SearchNetworkQueryResponseItem)}
  <a class="link" href={route('/[lang]/network/[networkId]', { lang: page.params.lang, networkId: row.id })}>
    {value}
  </a>
{/snippet}

<div class="grid gap-5">
  <div class="text-2xl font-bold">{m('NETWORKS')}</div>
  <div class="max-h-[calc(100vh-250px)]"><Grid {columns} rows={data.networks} /></div>
</div>
