<script lang="ts">
  import type { HTMLLabelAttributes } from 'svelte/elements'
  import type { InputFormField } from './FormField.svelte'
  import Icon from '@iconify/svelte'

  let {
    formField,
    children,
    ...rest
  }: { formField: InputFormField<any>; children: any } & Omit<HTMLLabelAttributes, 'for'> = $props()
</script>

<label class="text-nowrap flex items-center gap-1" for={formField.getId()} {...rest}>
  {@render children()}
  {#if formField.errors.length > 0}
    <span class="tooltip tooltip-error">
      <Icon class="text-error" icon="fa-solid:question-circle" />
      <div class="tooltip-content grid">
        {#each formField.errors as error}
          <div class="text-left">{error}</div>
        {/each}
      </div>
    </span>
  {:else}
    <Icon class="opacity-0 pointer-events-none" icon="fa-solid:question-circle" />
  {/if}
</label>
