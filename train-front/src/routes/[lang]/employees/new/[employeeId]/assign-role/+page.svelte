<script lang="ts">
  import { goto } from '$app/navigation'
  import { page } from '$app/state'
  import { route } from '$lib/ROUTES'
  import { EmployeeRole } from '$personnel-api/models/EmployeeRole'
  import { AssignEmployeeRoleUseCaseApi } from '$personnel-api/services/AssignEmployeeRoleUseCaseApi'
  import AssignRoleForm from '$lib/components/employees/AssignRoleForm.svelte'
  const employeesRoute = route('/[lang]/employees', { lang: page.params.lang })

  const submit = async (role: EmployeeRole) => {
    await AssignEmployeeRoleUseCaseApi.execute({ requestBody: { employeeId: page.params.employeeId, role } })
    goto(employeesRoute, { invalidateAll: true })
  }
</script>

<AssignRoleForm role={EmployeeRole.UNASSIGNED} onSubmit={submit} />
