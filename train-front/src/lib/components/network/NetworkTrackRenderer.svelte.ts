import type { GeoPositionBody } from "$network-api/models/GeoPositionBody";
import type { D3NetworkSelections } from "./D3NetworkSelections.svelte";
import { NetworkModel, type NetworkTrack } from "./NetworkModel.svelte";
import * as d3 from 'd3'
import { PositionMapper } from "./PositionMapper";

const d3line = d3
    .line<GeoPositionBody>()
    .x(d => PositionMapper.toX(d.longitude))
    .y(d => PositionMapper.toY(d.latitude))

export class NetworkTrackRenderer {

    constructor(
        readonly selections: D3NetworkSelections,
        readonly model: NetworkModel
    ) {
    }

    private onTrackClicked(track: NetworkTrack) {
        if (this.model.isTrackSelected(track)) {
            console.log("deselect")
            this.model.deselectTrack()
        } else {
            this.model.selectTrack(track)
        }
    }

    draw() {
        this.selections.root
            .selectAll('path.track')
            .data(this.model.tracks)
            .join('path')
            .attr('class', 'track')
            .attr('fill', 'none')
            .attr('stroke', data => (this.model.isTrackSelected(data) ? 'red' : 'gray'))
            .attr('stroke-width', 3)
            .attr('d', track => d3line(this.model.asPath(track)))

        this.selections.root
            .selectAll('path.track-selection')
            .data(this.model.tracks)
            .join('path')
            .attr('class', 'track-selection')
            .attr('fill', 'none')
            .attr('stroke', 'transparent')
            .attr('stroke-width', 15)
            .attr('d', track => d3line(this.model.asPath(track)))
            .on('click', (_, track) => this.onTrackClicked(track))

    }
}