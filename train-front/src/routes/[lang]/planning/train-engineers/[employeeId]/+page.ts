import type { SearchTrainEngineersDetailQueryResponse } from "$planning-api/models/SearchTrainEngineersDetailQueryResponse"
import { SearchTrainEngineersDetailQueryApi } from "$planning-api/services/SearchTrainEngineersDetailQueryApi"

export type LoadData = {
    details: SearchTrainEngineersDetailQueryResponse
}

export const load = async ({ params }) => {
    return {
        details: await SearchTrainEngineersDetailQueryApi.query({ employeeId: params.employeeId })
    } satisfies LoadData
}
