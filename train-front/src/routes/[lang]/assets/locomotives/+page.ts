import type { SearchLocomotivesQueryResponseItem } from "$assets-api/models/SearchLocomotivesQueryResponseItem"
import { SearchLocomotivesQueryApi } from "$assets-api/services/SearchLocomotivesQueryApi"

export type LoadData = {
    locomotives: Promise<SearchLocomotivesQueryResponseItem[]>
}

export const load = async () => {
    return {
        locomotives: SearchLocomotivesQueryApi.query({})
    } satisfies LoadData
}
