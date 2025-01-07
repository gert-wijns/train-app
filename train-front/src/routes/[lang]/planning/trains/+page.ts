import type { SearchTrainsQueryResponseItem } from '$planning-api/models/SearchTrainsQueryResponseItem';
import type { PageLoad } from "./$types";
import { SearchTrainsQueryApi } from '$planning-api/services/SearchTrainsQueryApi'

export type LoadData = {
    trains: Promise<SearchTrainsQueryResponseItem[]>
}

export const load: PageLoad = async () => {
    return {
        trains: SearchTrainsQueryApi.query(),
    } satisfies LoadData
}
