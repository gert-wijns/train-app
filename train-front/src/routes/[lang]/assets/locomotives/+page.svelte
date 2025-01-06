<script lang="ts">
  import { goto } from '$app/navigation'
  import { page } from '$app/state'
  import type { SearchLocomotivesQueryResponseItem } from '$assets-api/models/SearchLocomotivesQueryResponseItem.js'
  import { createKeydownListener } from '$lib/common/keyListener.js'
  import Grid, { type Column } from '$lib/grid/Grid.svelte'
  import { M, m } from '$lib/i18n/translate.svelte.js'
  import { route } from '$lib/ROUTES.js'
  import { onMount } from 'svelte'
  import { type LoadData } from './+page.js'

  let { data }: { data: LoadData } = $props()

  const addLocomotiveHref = route('/[lang]/assets/locomotives/add', { lang: page.params.lang })
  let columns: Column<SearchLocomotivesQueryResponseItem>[] = [
    {
      title: m('SERIAL_NUMBER'),
      get: row => row.serialNumber,
      snippet: detailLinks,
    },
    {
      title: m('NAME'),
      get: row => row.name,
    },
    {
      title: m('MODEL'),
      get: row => row.model.name,
    },
    {
      title: m('GAUGE'),
      get: row => row.model.gauge,
    },
    {
      title: m('STATUS'),
      get: row => row,
      snippet: status,
    },
  ]

  onMount(createKeydownListener('a', () => goto(addLocomotiveHref)))
</script>

{#snippet status(value: SearchLocomotivesQueryResponseItem)}
  {#if value.decommissioned}
    <span class="badge badge-sm badge-error">
      {M('DECOMMISSIONED')}
    </span>
  {/if}
{/snippet}
{#snippet detailLinks(value: string, row: SearchLocomotivesQueryResponseItem)}
  <a
    class="link"
    href={route('/[lang]/assets/locomotives/[locomotiveId]', { lang: page.params.lang, locomotiveId: row.id })}>
    {value}
  </a>
{/snippet}

<div class="grid gap-5">
  <div class="text-2xl font-bold">{m('LOCOMOTIVES')}</div>
  <div class="max-h-[calc(100vh-250px)]"><Grid {columns} rows={data.locomotives} /></div>
  <div class="grid">
    <a href={addLocomotiveHref} class="ml-auto btn btn-primary">{m('ADD')}</a>
  </div>
</div>
