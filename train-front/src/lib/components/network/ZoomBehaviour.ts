import type { D3NetworkSelections } from "./D3NetworkSelections.svelte"
import * as d3 from 'd3'

export class ZoomBehaviour {

    zoom: d3.ZoomBehavior<any, any>

    constructor(selections: D3NetworkSelections) {
        this.zoom = d3.zoom()
            .scaleExtent([0.1, 10])
            .on('zoom', e => selections.root.attr('transform', e.transform))
        this.extent(100, 100)
    }

    extent(width: number, height: number) {
        this.zoom.extent([
            [0, 0],
            [width, height],
        ])
    }
}