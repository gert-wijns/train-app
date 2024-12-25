<script lang="ts">
  import Grid, { type Column } from '$lib/grid/Grid.svelte'
  import { m } from '$lib/i18n/translate.svelte.js'
  import { type SearchEmployeesQueryResponseItem } from '$personnel-api/models/SearchEmployeesQueryResponseItem.js'
  import Icon from '@iconify/svelte'
  import { type LoadData } from './+page.js'
  import AssignRoleDialog from './AssignRoleDialog.svelte'
  import NewEmployeeDialog from './NewEmployeeDialog.svelte'
  import { SearchEmployeesQueryApi } from '$personnel-api/services/SearchEmployeesQueryApi.js'
  import type { EmployeeId } from '$personnel-api/models/EmployeeId.js'

  let { data }: { data: LoadData } = $props()

  let columns: Column<SearchEmployeesQueryResponseItem>[] = [
    {
      title: m('FIRST_NAME'),
      get: row => row.fullName.firstName,
    },
    {
      title: m('LAST_NAME'),
      get: row => row.fullName.lastName,
    },
    {
      title: m('ROLE'),
      get: row => row.role,
      snippet: roleRenderer,
    },
  ]

  let assignRoleDialog: AssignRoleDialog
  let newEmployeeDialog: NewEmployeeDialog
</script>

<AssignRoleDialog bind:this={assignRoleDialog} />
<NewEmployeeDialog bind:this={newEmployeeDialog} />

{#snippet roleRenderer(value: string, row: SearchEmployeesQueryResponseItem)}
  <span class="flex gap-1">
    {m(value as any)}

    <button
      class="btn btn-xs btn-ghost btn-circle h-4 w-5"
      aria-label="assign-role"
      onclick={() => assignRoleDialog.showDialog(row)}>
      <Icon class="h-4 w-4" icon="mynaui:edit-solid" />
    </button>
  </span>
{/snippet}

<div class="grid gap-5">
  <div class="text-2xl font-bold">{m('EMPLOYEES')}</div>
  <div class="max-h-[300px]"><Grid {columns} rows={data.employees} /></div>
  <div class="grid">
    <button class="ml-auto btn btn-primary" onclick={() => newEmployeeDialog.showDialog()}>{m('NEW')}</button>
  </div>
</div>
