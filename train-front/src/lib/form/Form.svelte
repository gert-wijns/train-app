<script lang="ts" generics="T extends Model">
  import type { HTMLFormAttributes } from 'svelte/elements'
  import type { FormModel } from './FormModel.svelte'
  import type { Model } from './FormField.svelte'
  import { registerDevAutofill } from '$lib/dev/DevAutofillHook.svelte'
  import { onMount } from 'svelte'

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

  let hiddenInputEl: HTMLInputElement
  onMount(() =>
    registerDevAutofill(() => {
      // focus hidden input at auto fill so we can submit with enter after
      hiddenInputEl?.focus()
    }),
  )
</script>

<form id={formModel.model.getId()} {...rest} {onsubmit}>
  <input bind:this={hiddenInputEl} tabindex={-1} class="absolute opacity-0 pointer-events-none" />
  {@render children()}
</form>
{#if showError}
  <div role="alert" class="alert alert-error mt-2">
    <span>{formModel.error}</span>
  </div>
{/if}
