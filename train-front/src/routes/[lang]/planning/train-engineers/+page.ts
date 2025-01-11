import type { PageLoad } from "./$types";
import { SearchTrainEngineersQueryApi } from '$planning-api/services/SearchTrainEngineersQueryApi';
import type { SearchTrainEngineersResponseItem } from "$planning-api/models/SearchTrainEngineersResponseItem";

export type LoadData = {
    trainEngineers: Promise<SearchTrainEngineersResponseItem[]>
}

export const load: PageLoad = async () => {
    return {
        trainEngineers: SearchTrainEngineersQueryApi.query(),
    } satisfies LoadData
}
