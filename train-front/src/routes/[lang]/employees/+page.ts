import { SearchEmployeesQueryApi, type SearchEmployeesQueryResponseItem } from "$personnel-api/index.js";

export type LoadData = {
    employees: Promise<SearchEmployeesQueryResponseItem[]>
}

export const load = async () => {
    return {
        employees: SearchEmployeesQueryApi.query({})
    } satisfies LoadData
}
