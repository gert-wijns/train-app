<script lang="ts">
  import { m } from '$lib/i18n/translate.svelte'
  import type { EmployeeRole } from '$personnel-api/models/EmployeeRole'
  import EmployeeRoleSelect from './EmployeeRoleSelect.svelte'

  let { role, onSubmit }: { role: EmployeeRole; onSubmit: (role: EmployeeRole) => void } = $props()
  let selected = $state(role) as EmployeeRole
</script>

<form class="grid gap-4">
  <div class="text-lg font-bold">{m('ASSIGN_ROLE')}</div>
  <EmployeeRoleSelect bind:selected />
  <button type="submit" onclick={() => onSubmit(selected)} class="btn btn-primary ml-auto">{m('ASSIGN')}</button>
</form>
