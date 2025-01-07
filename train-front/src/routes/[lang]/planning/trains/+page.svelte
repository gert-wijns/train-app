<script lang="ts">
  import { goto } from '$app/navigation'
  import { page } from '$app/state'
  import { createKeydownListener } from '$lib/common/keyListener.js'
  import Grid, { type Column } from '$lib/grid/Grid.svelte'
  import { M, m } from '$lib/i18n/translate.svelte.js'
  import { route } from '$lib/ROUTES.js'
  import { onMount } from 'svelte'
  import { type LoadData } from './+page.js'
  import type { SearchTrainsQueryResponseItem } from '$planning-api/models/SearchTrainsQueryResponseItem'

  let { data }: { data: LoadData } = $props()

  const addTrainHref = route('/[lang]/planning/trains/add', { lang: page.params.lang })
  let columns: Column<SearchTrainsQueryResponseItem>[] = [
    {
      title: m('SERIAL_NUMBER'),
      get: row => row.locomotive.serialNumber,
      snippet: detailLinks,
    },
    {
      title: m('STATUS'),
      get: row => row,
      snippet: status,
    },
  ]

  onMount(createKeydownListener('a', () => goto(addTrainHref)))
</script>

{#snippet status(value: SearchTrainsQueryResponseItem)}
  {#if value.containsDecommissioned}
    <span class="badge badge-sm badge-warning">
      {M('DECOMMISSIONED')}
    </span>
  {/if}
{/snippet}

{#snippet detailLinks(value: string, row: SearchTrainsQueryResponseItem)}
  <a class="link" href={route('/[lang]/planning/trains/[trainId]', { lang: page.params.lang, trainId: row.id })}>
    {value}
  </a>
{/snippet}

<div class="grid gap-5">
  <div class="text-2xl font-bold">{m('LOCOMOTIVES')}</div>
  <div class="max-h-[calc(100vh-250px)]"><Grid {columns} rows={data.trains} /></div>
  <div class="grid">
    <a href={addTrainHref} class="ml-auto btn btn-primary">{m('ADD')}</a>
  </div>
</div>
