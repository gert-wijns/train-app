<script module>
  import { type Component, type Snippet } from 'svelte'
  import Cell from './Cell.svelte'

  export interface Column<T, V = any> {
    title: string
    get: (row: T) => V
    snippet?: Snippet<[V, T]>
    component?: Component<{ value: V; row: T }>
  }
</script>

<script lang="ts">
  type T = $$Generic

  interface Props<T> {
    columns: Column<T>[]
    rows: Promise<T[]>
  }

  let props: Props<T> & any = $props()
</script>

<div class="h-full w-full overflow-auto shadow">
  <table class="table table-sm table-pin-rows rounded-none border border-collapse border-base-300 overflow-hidden">
    <thead class="border-b-2 border-base-300">
      <tr class="bg-base-200">
        {#each props.columns as column}
          <th class="border-r border-base-300">{column.title}</th>
        {/each}
      </tr>
    </thead>
    <tbody>
      {#await props.rows}
        <tr><td colspan={props.columns.length}>Loading...</td></tr>
      {:then rows}
        {#each rows as row}
          <tr class="border-b border-base-300 hover:bg-base-200 hover:cursor-pointer bg-base-100">
            {#each props.columns as { snippet, component, get }}
              <td class="border-r border-base-300">
                {#if snippet}
                  {@render snippet(get(row), row)}
                {:else if component}
                  <Cell value={get(row)} {row} {component} />
                {:else}
                  {get(row)}
                {/if}
              </td>
            {/each}
          </tr>
        {/each}
      {:catch ex}
        <tr>
          <td class="text-error" colspan={props.columns.length}>[{ex.status}] Something went wrong while loading...</td>
        </tr>
      {/await}
    </tbody>
  </table>
</div>
