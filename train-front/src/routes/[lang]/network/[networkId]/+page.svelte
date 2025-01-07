<script lang="ts">
  import { m } from '$lib/i18n/translate.svelte'
  import * as d3 from 'd3'
  import { onMount } from 'svelte'
  import type { LoadData } from './+page'
  import { PositionMapper } from '$lib/components/network/PositionMapper'
  import { NetworkModel } from '$lib/components/network/NetworkModel.svelte'
  import { DragNodeBehaviour } from '$lib/components/network/DragNodeBehaviour.svelte'
  import { D3NetworkSelections } from '$lib/components/network/D3NetworkSelections.svelte'
  import { NetworkNodeRenderer } from '$lib/components/network/NetworkNodeRenderer.svelte'
  import { NetworkTrackRenderer } from '$lib/components/network/NetworkTrackRenderer.svelte'
  import { ZoomBehaviour } from '$lib/components/network/ZoomBehaviour'
  import { registerKeyListener } from '$lib/components/network/NetworkKeyListener'
  import { page } from '$app/state'

  let props: { data: LoadData } = $props()
  let selections = new D3NetworkSelections()

  let model = new NetworkModel(page.params.networkId, props.data.nodes, props.data.tracks)
  let dragNodeBehaviour = new DragNodeBehaviour(selections)
  let nodeRenderer = new NetworkNodeRenderer(selections, model, dragNodeBehaviour)
  let trackRenderer = new NetworkTrackRenderer(selections, model)
  let zoomBehaviour = new ZoomBehaviour(selections)

  let divEl = $state() as HTMLDivElement
  let width = $derived(divEl?.clientWidth || 100)
  let height = $derived(divEl?.clientHeight || 100)
  $effect(() => zoomBehaviour.extent(width, height))
  $effect(() => registerKeyListener(model))

  onMount(() => {
    selections.root = d3.select('svg#network-svg g')
    selections.svg = d3.select('svg#network-svg')
    selections.svg.call(zoomBehaviour.zoom)
    selections.svg.on('click', event => {
      const [x, y] = d3.pointer(event, selections.root.node())
      model.selectGeoPosition(PositionMapper.toLongitude(x), PositionMapper.toLatitude(y))
    })
    selections.root.on('click', event => event.stopPropagation())
  })

  $effect(() => {
    trackRenderer.draw()
    nodeRenderer.draw()
  })
</script>

<div class="grid gap-5">
  <div class="text-2xl font-bold">{m('NETWORK')}</div>
  <div bind:this={divEl} class="w-full h-[calc(100vh-250px)]">
    <svg id="network-svg" class="min-w-full bg-gray-200 h-full" height="100">
      <g cursor="pointer"></g>
    </svg>
  </div>
</div>
