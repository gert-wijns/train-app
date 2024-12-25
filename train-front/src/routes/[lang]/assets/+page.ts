import { SearchAssetsQueryApi, type SearchAssetsQueryResponseItem } from "$assets-api"


export type LoadData = {
    assets: Promise<SearchAssetsQueryResponseItem[]>
}

export const load = async () => {
    return {
        assets: SearchAssetsQueryApi.query()
    } satisfies LoadData
}
