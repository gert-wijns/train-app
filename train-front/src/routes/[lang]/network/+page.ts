import type { SearchNetworkQueryResponseItem } from '$network-api/models/SearchNetworkQueryResponseItem';
import type { PageLoad } from "./$types";
import { SearchNetworkQueryApi } from '$network-api/services/SearchNetworkQueryApi'

export type LoadData = {
    networks: Promise<SearchNetworkQueryResponseItem[]>
}

export const load: PageLoad = async () => {
    return {
        networks: SearchNetworkQueryApi.query(),
    } satisfies LoadData
}
