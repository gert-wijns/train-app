import * as d3 from 'd3'
import { type NetworkNode } from "./NetworkModel.svelte";
import { RepositionNodeUseCaseApi } from '$network-api/services/RepositionNodeUseCaseApi';
import { D3NetworkSelections } from './D3NetworkSelections.svelte';
import { PositionMapper } from './PositionMapper';

export class DragNodeBehaviour {
    dragStartX: number = 0
    dragStartY: number = 0
    initial: NetworkNode | undefined

    constructor(readonly selections: D3NetworkSelections) {
    }

    dragStarted(event: DragEvent, d: NetworkNode) {
        this.dragStartX = event.x
        this.dragStartY = event.y
        this.initial = $state.snapshot(d)
        this.selections.root.attr('cursor', 'grabbing')
    }

    dragged(event: DragEvent, d: NetworkNode) {
        d.geoPosition = this.calculateNewGeoPosition(event)
    }

    dragended(event: DragEvent, d: NetworkNode) {
        this.selections.root.attr('cursor', 'pointer')
        d.geoPosition = this.calculateNewGeoPosition(event)
        RepositionNodeUseCaseApi.execute({
            requestBody: {
                id: d.id,
                newGeoPosition: d.geoPosition
            }
        })
    }

    dragCallable() {
        const drag = d3.drag<Element, NetworkNode>()
        drag.on('start', (event: DragEvent, d: NetworkNode) => this.dragStarted(event, d))
        drag.on('drag', (event: DragEvent, d: NetworkNode) => this.dragged(event, d))
        drag.on('end', (event: DragEvent, d: NetworkNode) => this.dragended(event, d))
        return drag
    }

    private calculateNewGeoPosition(event: DragEvent) {
        const diffLongitude = PositionMapper.toLongitude(event.x - this.dragStartX)
        const diffLatitude = PositionMapper.toLatitude(event.y - this.dragStartY)
        return {
            longitude: this.initial!.geoPosition.longitude + diffLongitude,
            latitude: this.initial!.geoPosition.latitude + diffLatitude,
        }
    }
}