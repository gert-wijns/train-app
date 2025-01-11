import type { SearchWagonsQueryResponseItem } from "$assets-api/models/SearchWagonsQueryResponseItem"
import { SearchWagonsQueryApi } from "$assets-api/services/SearchWagonsQueryApi"


export type LoadData = {
    wagon: SearchWagonsQueryResponseItem
}

export const load = async ({ params }) => {
    return {
        wagon: await SearchWagonsQueryApi
            .query({ wagonId: [params.wagonId] })
            .then(wagons => wagons[0])
    } satisfies LoadData
}
