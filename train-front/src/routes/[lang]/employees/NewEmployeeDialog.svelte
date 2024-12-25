<script lang="ts">
  import { invalidateAll } from '$app/navigation'
  import { m } from '$lib/i18n/translate.svelte'
  import { v4 as uuidv4 } from 'uuid'
  import EmployeeRoleSelect from './EmployeeRoleSelect.svelte'
  import type { EmployeeId } from '$planning-api/models/EmployeeId'
  import type { FullNameBody } from '$personnel-api/models/FullNameBody'
  import type { EmployeeRole } from '$personnel-api/models/EmployeeRole'
  import { NewEmployeeUseCaseApi } from '$personnel-api/services/NewEmployeeUseCaseApi'
  import { AssignEmployeeRoleUseCaseApi } from '$personnel-api/services/AssignEmployeeRoleUseCaseApi'
  import { SearchEmployeesQueryApi } from '$personnel-api/services/SearchEmployeesQueryApi'
  import NewEmployeeForm from './NewEmployeeForm.svelte'
  import AssignRoleForm from './AssignRoleForm.svelte'

  let step = 1
  let dialogEl: HTMLDialogElement = $state() as HTMLDialogElement
  let employee: { id: EmployeeId; role: EmployeeRole } | undefined = $state()

  export function showDialog() {
    employee = undefined
    dialogEl.showModal()
  }
  const submitStep1 = async (fullName: FullNameBody) => {
    const employeeId = uuidv4()
    await NewEmployeeUseCaseApi.execute({ requestBody: { employeeId, fullName } })
    employee = (await SearchEmployeesQueryApi.query({ employeeId: [employeeId] }))[0]
  }
  const submitStep2 = async (role: EmployeeRole) => {
    await AssignEmployeeRoleUseCaseApi.execute({ requestBody: { employeeId: employee!.id, role } })
    invalidateAll()
    dialogEl.close()
  }
</script>

<dialog bind:this={dialogEl} class="modal">
  <div class="grid modal-box">
    <div class="py-3 w-full grid">
      <ul class="steps ml-auto mr-auto">
        <li class="step px-5" class:step-primary={employee === undefined}>{m('ENTER_BIO')}</li>
        <li class="step" class:step-primary={employee !== undefined}>{m('ASSIGN_ROLE')}</li>
      </ul>
    </div>
    {#if employee === undefined}
      <NewEmployeeForm onSubmit={submitStep1} />
    {:else}
      <AssignRoleForm role={employee.role} onSubmit={submitStep2} />
    {/if}
  </div>
  <form method="dialog" class="modal-backdrop">
    <button>close</button>
  </form>
</dialog>
