<script lang="ts">
  import { InputFormField } from '$lib/form/FormField.svelte'

  let options = Object.values(['GWR_4900', 'GWR_4073', 'LNR_B1'])
    .map(modelTypeId => ({ label: modelTypeId, value: modelTypeId }))
    .sort((o1, o2) => o1.label.toLowerCase().localeCompare(o2.label.toLowerCase()))

  let { formField }: { formField: InputFormField<string> } = $props()

  export function getOptions() {
    return options
  }
</script>

<div class="grid gap-1">
  {#each options as { label, value }}
    <label class="flex items-center gap-1">
      <input
        type="radio"
        class="radio radio-xs"
        checked={formField.input === value}
        onchange={_ => (formField.input = value)} />
      {label}
    </label>
  {/each}
</div>
