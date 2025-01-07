import { AddNodeUseCaseApi, AddTrackUseCaseApi, DecommissionNodeUseCaseApi, DecommissionTrackUseCaseApi, type NetworkId } from "$network-api"
import type { GeoPositionBody } from "$network-api/models/GeoPositionBody"
import type { NodeId } from "$network-api/models/NodeId"
import { SpeedBody } from "$network-api/models/SpeedBody"
import { v4 as uuidv4 } from 'uuid'

export type NetworkNode = {
    id: NodeId
    networkId: NetworkId
    name: string
    geoPosition: GeoPositionBody
}

export type NetworkTrack = {
    fromNodeId: NodeId
    toNodeId: NodeId
    electrified: boolean
    slope: number
    speedLimit: SpeedBody
    gauge: string
}

export class NetworkModel {
    networkId: NetworkId
    nodes = $state([]) as NetworkNode[]
    tracks = $state([]) as NetworkTrack[]
    selectedNodeId = $state<NodeId>()
    selectedTrackId = $state<{ fromNodeId: NodeId, toNodeId: NodeId }>()

    constructor(networkId: NetworkId, nodes: NetworkNode[], tracks: NetworkTrack[]) {
        this.networkId = networkId
        this.nodes = nodes
        this.tracks = tracks
    }

    getNode(nodeId: NodeId) {
        const node = this.nodes.find(node => node.id === nodeId)
        if (node === undefined) {
            throw Error("No node found for nodeId: " + nodeId)
        }
        return node
    }

    asPath(track: NetworkTrack) {
        const fromGeoPosition = this.getNode(track.fromNodeId).geoPosition
        const toGeoPosition = this.getNode(track.toNodeId).geoPosition
        return [fromGeoPosition, toGeoPosition]
    }

    decommissionSelectedNode(): void {
        const id = this.selectedNodeId
        if (id) {
            DecommissionNodeUseCaseApi.execute({
                requestBody: { id }
            })
            this.selectedNodeId = undefined
        }
    }

    decommissionSelectTrack(): void {
        const selectedTrackId = this.selectedTrackId
        if (selectedTrackId) {
            const { fromNodeId, toNodeId } = selectedTrackId
            DecommissionTrackUseCaseApi.execute({
                requestBody: {
                    fromNodeId,
                    toNodeId
                }
            })

            this.selectedTrackId = undefined
        }
    }

    cancelActiveBehaviour() {
        this.selectedNodeId = undefined
    }

    isNodeSelected(node: NetworkNode) {
        return this.selectedNodeId === node.id
    }

    deselectNode() {
        this.selectedNodeId = undefined
    }

    selectNode(node: NetworkNode): void {
        if (this.selectedNodeId) {
            this.addTrack(this.selectedNodeId, node.id)
        }
        this.selectedNodeId = node.id
    }

    deselectTrack() {
        this.selectedTrackId = undefined
    }

    selectTrack({ fromNodeId, toNodeId }: NetworkTrack): void {
        this.selectedTrackId = { fromNodeId, toNodeId }
    }

    isTrackSelected(track: NetworkTrack) {
        return this.selectedTrackId
            && this.selectedTrackId.fromNodeId === track.fromNodeId
            && this.selectedTrackId.toNodeId === track.toNodeId
    }

    selectGeoPosition(longitude: number, latitude: number) {
        const newNode: NetworkNode = {
            id: uuidv4(),
            networkId: this.networkId,
            name: '',
            geoPosition: {
                longitude: longitude,
                latitude: latitude,
            },
        }
        // error handling?
        AddNodeUseCaseApi.execute({ requestBody: newNode })
        this.nodes.push(newNode)

        if (this.selectedNodeId) {
            this.addTrack(this.selectedNodeId, newNode.id)
        }

        this.selectedNodeId = newNode.id
        this.selectedTrackId = undefined
    }

    private addTrack(fromNodeId: string, toNodeId: string) {
        const newTrack: NetworkTrack = {
            fromNodeId,
            toNodeId,
            slope: 0,
            speedLimit: { speed: 50, measurement: SpeedBody.measurement.KPH },
            gauge: '1335mm',
            electrified: true,
        }
        // error handling?
        AddTrackUseCaseApi.execute({ requestBody: newTrack })
        this.tracks.push(newTrack)
    }
}