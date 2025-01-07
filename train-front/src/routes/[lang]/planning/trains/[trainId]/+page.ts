import type { SearchWagonModelsQueryResponseItem } from "$assets-api/models/SearchWagonModelsQueryResponseItem"
import { SearchWagonModelsQueryApi } from "$assets-api/services/SearchWagonModelsQueryApi"
import type { SearchTrainDetailsQueryResponse } from "$planning-api"
import { SearchTrainDetailsQueryApi } from "$planning-api"


export type LoadData = {
    train: SearchTrainDetailsQueryResponse
}

export const load = async ({ params }) => {
    return {
        train: await SearchTrainDetailsQueryApi.query({ trainId: params.trainId })
    } satisfies LoadData
}
