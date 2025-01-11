<script lang="ts">
  import { goto } from '$app/navigation'
  import { page } from '$app/state'
  import Grid, { type Column } from '$lib/grid/Grid.svelte'
  import { m } from '$lib/i18n/translate.svelte.js'
  import { route } from '$lib/ROUTES'
  import { type SearchEmployeesQueryResponseItem } from '$personnel-api/models/SearchEmployeesQueryResponseItem.js'
  import Icon from '@iconify/svelte'
  import { type LoadData } from './+page'
  import type { EmployeeId } from '$personnel-api/models/EmployeeId'

  let { children, data }: { children: any; data: LoadData } = $props()

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

  const gotoNew = () => {
    goto(route('/[lang]/employees/new', { lang: page.params.lang }))
  }
  const gotoAssignRole = (employeeId: EmployeeId) => {
    goto(route('/[lang]/employees/[employeeId]/assign-role', { lang: page.params.lang, employeeId }))
  }
</script>

{#snippet roleRenderer(value: string, row: SearchEmployeesQueryResponseItem)}
  <span class="flex gap-1">
    {m(value as any)}

    <button
      class="btn btn-xs btn-ghost btn-circle h-4 w-5"
      aria-label="assign-role"
      onclick={() => gotoAssignRole(row.id)}>
      <Icon class="h-4 w-4" icon="mynaui:edit-solid" />
    </button>
  </span>
{/snippet}

<div class="grid gap-5">
  <div class="text-2xl font-bold">{m('EMPLOYEES')}</div>
  <div class="max-h-[calc(100vh-250px)]"><Grid {columns} rows={data.employees} /></div>
  <div class="grid">
    <button class="ml-auto btn btn-primary" onclick={gotoNew}>{m('NEW')}</button>
  </div>
</div>
