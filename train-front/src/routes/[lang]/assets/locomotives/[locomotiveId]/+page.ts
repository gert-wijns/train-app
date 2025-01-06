import type { SearchLocomotivesQueryResponseItem } from "$assets-api/models/SearchLocomotivesQueryResponseItem"
import { SearchLocomotivesQueryApi } from "$assets-api/services/SearchLocomotivesQueryApi"


export type LoadData = {
    locomotive: SearchLocomotivesQueryResponseItem
}

export const load = async ({ params }) => {
    return {
        locomotive: await SearchLocomotivesQueryApi
            .query({ locomotiveId: [params.locomotiveId] })
            .then(locomotives => locomotives[0])
    } satisfies LoadData
}
