<script lang="ts">
  import { mount, type Component } from 'svelte'

  type V = $$Generic
  type R = $$Generic
  type ComponentProps = { value: V; row: R }
  let { value, row, component }: { value: V; row: R; component: Component<ComponentProps> } = $props()

  let cellEl: HTMLDivElement = $state() as HTMLDivElement
  $effect(() => {
    if (cellEl) {
      mount(component, { target: cellEl, props: { value, row } })
    }
  })
</script>

<div bind:this={cellEl}></div>
