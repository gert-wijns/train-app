import type { SearchWagonModelsQueryResponseItem } from "$assets-api/models/SearchWagonModelsQueryResponseItem"
import { SearchWagonModelsQueryApi } from "$assets-api/services/SearchWagonModelsQueryApi"


export type LoadData = {
    models: Promise<SearchWagonModelsQueryResponseItem[]>
}

export const load = async () => {
    return {
        models: SearchWagonModelsQueryApi.query()
    } satisfies LoadData
}
