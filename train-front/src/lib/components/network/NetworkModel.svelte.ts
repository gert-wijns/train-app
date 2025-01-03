import { AddNodeUseCaseApi, AddTrackUseCaseApi, DeconstructNodeUseCaseApi, DeconstructTrackUseCaseApi, RepositionNodeUseCaseApi } from "$network-api"
import type { GeoPositionBody } from "$network-api/models/GeoPositionBody"
import type { NodeId } from "$network-api/models/NodeId"
import { SpeedBody } from "$network-api/models/SpeedBody"
import { v4 as uuidv4 } from 'uuid'

export type NetworkNode = {
    id: NodeId
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
    nodes = $state([]) as NetworkNode[]
    tracks = $state([]) as NetworkTrack[]
    selectedNodeId = $state<NodeId>()
    selectedTrackId = $state<{ fromNodeId: NodeId, toNodeId: NodeId }>()

    constructor(nodes: NetworkNode[], tracks: NetworkTrack[]) {
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

    deconstructSelectedNode(): void {
        const id = this.selectedNodeId
        if (id) {
            this.nodes.splice(this.nodes.findIndex(entry => entry.id === id), 1)

            DeconstructNodeUseCaseApi.execute({
                requestBody: { id }
            })
            this.selectedNodeId = undefined
        }
    }

    deconstructSelectTrack(): void {
        const selectedTrackId = this.selectedTrackId
        if (selectedTrackId) {
            const { fromNodeId, toNodeId } = selectedTrackId
            this.tracks.splice(this.tracks.findIndex(entry =>
                entry.fromNodeId === fromNodeId &&
                entry.toNodeId === toNodeId), 1)

            DeconstructTrackUseCaseApi.execute({
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