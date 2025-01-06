import type { SearchLocomotiveModelsQueryResponseItem } from "$assets-api/models/SearchLocomotiveModelsQueryResponseItem"
import { SearchLocomotiveModelsQueryApi } from "$assets-api/services/SearchLocomotiveModelsQueryApi"


export type LoadData = {
    models: Promise<SearchLocomotiveModelsQueryResponseItem[]>
}

export const load = async () => {
    return {
        models: SearchLocomotiveModelsQueryApi.query()
    } satisfies LoadData
}
