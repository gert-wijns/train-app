<script lang="ts" generics="T extends FormField<any>">
  import type { HTMLFormAttributes } from 'svelte/elements'
  import type { FormModel } from './FormModel.svelte'
  import type { FormField } from './FormField.svelte'
  import OpenDialog from '$lib/dialog/OpenDialog.svelte'

  let {
    formModel,
    children,
    ...rest
  }: { formModel: FormModel<T>; children: any } & Omit<HTMLFormAttributes, 'onsubmit'> = $props()

  let showError = $state(false)
  const onsubmit = async () => {
    await formModel.onSubmit()
    showError = !!formModel.error
  }
</script>

{#if showError}
  <OpenDialog class="modal ml-auto min-w-24 mr-auto [&>div]:bg-error [&>div]:w-min" cancel={() => (showError = false)}>
    <div class="whitespace-nowrap text-error-content">
      {formModel.error}
    </div>
  </OpenDialog>
{/if}

<form {...rest} {onsubmit}>
  {@render children()}
</form>
