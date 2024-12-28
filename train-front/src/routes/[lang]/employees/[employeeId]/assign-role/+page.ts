import { SearchEmployeesQueryApi, type SearchEmployeesQueryResponseItem } from "$personnel-api/index.js";
import type { PageLoad } from "./$types";

export type LoadData = {
    employee: SearchEmployeesQueryResponseItem
}

export const load: PageLoad = async ({ params }) => {
    return {
        employee: (await SearchEmployeesQueryApi.query({ employeeId: [params.employeeId] }))[0]
    } satisfies LoadData
}
