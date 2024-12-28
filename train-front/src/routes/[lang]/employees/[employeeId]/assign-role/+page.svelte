<script lang="ts">
  import { goto } from '$app/navigation'
  import { page } from '$app/state'
  import { route } from '$lib/ROUTES'
  import { AssignEmployeeRoleUseCaseApi } from '$personnel-api'
  import { EmployeeRole } from '$personnel-api/models/EmployeeRole'
  import AssignRoleForm from '$lib/components/employees/AssignRoleForm.svelte'
  import type { LoadData } from './+page'
  import OpenDialog from '$lib/dialog/OpenDialog.svelte'

  let { data }: { data: LoadData } = $props()
  const employeesRoute = route('/[lang]/employees', { lang: page.params.lang })

  const submit = async (role: EmployeeRole) => {
    await AssignEmployeeRoleUseCaseApi.execute({ requestBody: { employeeId: data.employee.id, role } })
    goto(employeesRoute, { invalidateAll: true })
  }
  const cancel = () => {
    window.history.back()
  }
</script>

<OpenDialog {cancel}>
  <AssignRoleForm role={data.employee.role} onSubmit={submit} />
</OpenDialog>
