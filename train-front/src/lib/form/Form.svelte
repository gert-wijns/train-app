<script lang="ts" generics="T extends FormField<any>">
  import type { HTMLFormAttributes } from 'svelte/elements'
  import type { FormModel } from './FormModel.svelte'
  import type { FormField } from './FormField.svelte'

  let {
    formModel,
    children,
    ...rest
  }: { formModel: FormModel<T>; children: any } & Omit<HTMLFormAttributes, 'onsubmit'> = $props()

  let showError = $state(false)
  const onsubmit = async () => {
    showError = false
    await formModel.onSubmit()
    if (!!formModel.error) {
      showError = true
    }
  }
</script>

<form {...rest} {onsubmit}>
  {@render children()}
</form>
{#if showError}
  <div role="alert" class="alert alert-error mt-2">
    <span>{formModel.error}</span>
  </div>
{/if}
