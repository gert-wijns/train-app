import type { SearchWagonsQueryResponseItem } from "$assets-api/models/SearchWagonsQueryResponseItem"
import { SearchWagonsQueryApi } from "$assets-api/services/SearchWagonsQueryApi"


export type LoadData = {
    locomotive: Promise<SearchWagonsQueryResponseItem>
}

export const load = async ({ params }) => {
    return {
        locomotive: SearchWagonsQueryApi
            .query({ wagonId: [params.wagonId] })
            .then(wagons => wagons[0])
    } satisfies LoadData
}
