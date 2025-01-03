<script lang="ts">
  import type { HTMLInputAttributes } from 'svelte/elements'
  import type { InputFormField } from './FormField.svelte'

  let { formField, ...rest }: { formField: InputFormField<any> } & Omit<HTMLInputAttributes, 'value' | 'id'> = $props()
</script>

<input
  class="input block min-w-full"
  class:input-error={formField.errors.length > 0}
  id={formField.getId()}
  bind:value={formField.input}
  {...rest} />
  