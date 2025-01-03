import type { PageLoad } from "./$types";
import { type SearchNetworkNodesQueryResponseItem } from '$network-api/models/SearchNetworkNodesQueryResponseItem'
import { SearchNetworkNodesQueryApi } from '$network-api/services/SearchNetworkNodesQueryApi'
import { SearchNetworkTracksQueryApi } from '$network-api/services/SearchNetworkTracksQueryApi'
import type { SearchNetworkTracksQueryResponseItem } from "$network-api/models/SearchNetworkTracksQueryResponseItem";

export type LoadData = {
    nodes: SearchNetworkNodesQueryResponseItem[]
    tracks: SearchNetworkTracksQueryResponseItem[]
}

export const load: PageLoad = async () => {
    const [nodes, tracks] = await Promise.all([
        SearchNetworkNodesQueryApi.query(),
        SearchNetworkTracksQueryApi.query()])
    return {
        nodes,
        tracks
    } satisfies LoadData
}
