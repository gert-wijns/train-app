import type { PageLoad } from "../$types";
import { SearchNetworkNodesQueryApi } from '$network-api/services/SearchNetworkNodesQueryApi'
import { SearchNetworkTracksQueryApi } from '$network-api/services/SearchNetworkTracksQueryApi'
import type { SearchNetworkTracksQueryResponseItem } from "$network-api/models/SearchNetworkTracksQueryResponseItem";
import type { NetworkNode } from "$lib/components/network/NetworkModel.svelte";

export type LoadData = {
    nodes: NetworkNode[]
    tracks: SearchNetworkTracksQueryResponseItem[]
}

export const load: PageLoad = async () => {
    const [nodes, tracks] = await Promise.all([
        SearchNetworkNodesQueryApi.query(),
        SearchNetworkTracksQueryApi.query()])
    return {
        nodes: nodes.map(node => ({ ...node, networkId: node.network.id })),
        tracks
    } satisfies LoadData
}
