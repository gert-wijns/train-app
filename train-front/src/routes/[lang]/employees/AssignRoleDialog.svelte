<script module>
  import { invalidateAll } from '$app/navigation'
  import { m } from '$lib/i18n/translate.svelte'
  import { AssignEmployeeRoleUseCaseApi } from '$personnel-api'
  import type { EmployeeId } from '$personnel-api/models/EmployeeId'
  import { EmployeeRole } from '$personnel-api/models/EmployeeRole'
  import type { FullNameBody } from '$personnel-api/models/FullNameBody'
  import AssignRoleForm from './AssignRoleForm.svelte'
  import EmployeeRoleSelect from './EmployeeRoleSelect.svelte'

  export interface AssignRoleDialogInput {
    id: EmployeeId
    fullName: FullNameBody
    role: EmployeeRole
  }
</script>

<script lang="ts">
  let input: AssignRoleDialogInput = $state() as AssignRoleDialogInput
  let dialogEl: HTMLDialogElement = $state() as HTMLDialogElement

  export function showDialog(showInput: AssignRoleDialogInput) {
    input = showInput
    dialogEl.showModal()
  }
  const submit = (role: EmployeeRole) => {
    AssignEmployeeRoleUseCaseApi.execute({ requestBody: { employeeId: input.id, role } })
      .then(() => invalidateAll())
      .then(() => dialogEl.close())
  }
</script>

<dialog bind:this={dialogEl} class="modal">
  {#if input}
    <div class="modal-box">
      <AssignRoleForm role={input.role} onSubmit={submit} />
    </div>
    <form method="dialog" class="modal-backdrop">
      <button>close</button>
    </form>
  {/if}
</dialog>
