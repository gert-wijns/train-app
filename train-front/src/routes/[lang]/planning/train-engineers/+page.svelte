<script lang="ts">
  import { page } from '$app/state'
  import Grid, { type Column } from '$lib/grid/Grid.svelte'
  import { M, m } from '$lib/i18n/translate.svelte.js'
  import { route } from '$lib/ROUTES.js'
  import type { SearchTrainEngineersResponseItem } from '$planning-api/models/SearchTrainEngineersResponseItem.js'
  import { type LoadData } from './+page.js'

  let { data }: { data: LoadData } = $props()

  let columns: Column<SearchTrainEngineersResponseItem>[] = [
    {
      title: m('BADGE_NR'),
      get: row => row.id,
      snippet: detailLinks,
    },
    {
      title: m('STATUS'),
      get: row => row.active,
      snippet: status,
    },
  ]
</script>

{#snippet status(active: boolean)}
  {#if !active}
    <span class="badge badge-sm badge-error">
      {M('INACTIVE')}
    </span>
  {/if}
{/snippet}

{#snippet detailLinks(value: string, row: SearchTrainEngineersResponseItem)}
  <a
    class="link"
    href={route('/[lang]/planning/train-engineers/[employeeId]', { lang: page.params.lang, employeeId: row.id })}>
    {value}
  </a>
{/snippet}

<div class="grid gap-5">
  <div class="text-2xl font-bold">{m('TRAIN_ENGINEERS')}</div>
  <div class="max-h-[calc(100vh-250px)]"><Grid {columns} rows={data.trainEngineers} /></div>
</div>
