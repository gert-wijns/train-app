import type { D3NetworkSelections } from "./D3NetworkSelections.svelte";
import type { DragNodeBehaviour } from "./DragNodeBehaviour.svelte";
import { NetworkModel, type NetworkNode } from "./NetworkModel.svelte";
import { PositionMapper } from "./PositionMapper";

export class NetworkNodeRenderer {

    constructor(
        readonly selections: D3NetworkSelections,
        readonly model: NetworkModel,
        readonly dragNodeBehaviour: DragNodeBehaviour,
    ) {
    }

    private onNodeClicked(node: NetworkNode) {
        if (this.model.isNodeSelected(node)) {
            this.model.deselectNode()
        } else {
            this.model.selectNode(node)
        }
    }

    draw() {
        this.selections.root
            .selectAll('circle.node')
            .data(this.model.nodes)
            .join('circle')
            .attr('class', 'node')
            .attr('cx', node => PositionMapper.toX(node.geoPosition.longitude))
            .attr('cy', node => PositionMapper.toY(node.geoPosition.latitude))
            .attr('r', 10)
            .attr('fill', data => (this.model.selectedNodeId === data.id ? 'red' : 'blue'))

        this.selections.root
            .selectAll('circle.node-selection')
            .data(this.model.nodes)
            .join('circle')
            .attr('class', 'node-selection')
            .attr('title', node => node.name)
            .attr('cx', node => PositionMapper.toX(node.geoPosition.longitude))
            .attr('cy', node => PositionMapper.toY(node.geoPosition.latitude))
            .attr('r', 20)
            .attr('fill', 'transparent')
            .on('click', (_, node) => this.onNodeClicked(node))
            .call(this.dragNodeBehaviour.dragCallable() as any)

        this.selections.root
            .selectAll('text.node')
            .data(this.model.nodes)
            .join('text')
            .attr('class', 'node stroke-black stroke-1 text-2xl')
            .attr("text-anchor", "middle")
            .text(node => node.name)
            .attr('x', node => PositionMapper.toX(node.geoPosition.longitude))
            .attr('y', node => PositionMapper.toY(node.geoPosition.latitude) - 20)
            .on('click', (_, node) => this.onNodeClicked(node))
            .call(this.dragNodeBehaviour.dragCallable() as any)
    }
}