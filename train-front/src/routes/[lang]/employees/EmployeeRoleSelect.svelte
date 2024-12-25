<script lang="ts">
  import { m } from '$lib/i18n/translate.svelte'
  import { EmployeeRole } from '$personnel-api/models/EmployeeRole'

  let { selected = $bindable() }: { selected: EmployeeRole } = $props()

  let options = Object.values(EmployeeRole)
    .map(role => ({ label: m(role as any), value: role }))
    .sort((o1, o2) => o1.label.toLowerCase().localeCompare(o2.label.toLowerCase()))
</script>

<div class="grid gap-1">
  {#each options as { label, value }}
    <label class="flex items-center gap-1">
      <input
        type="radio"
        name="AssignRoleDialogInput-radio"
        class="radio radio-xs"
        checked={selected === value}
        onchange={_ => (selected = value)} />
      {label}
    </label>
  {/each}
</div>
