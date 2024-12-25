<script lang="ts">
  import { m } from '$lib/i18n/translate.svelte'
  import type { FullNameBody } from '$personnel-api/models/FullNameBody'

  let { onSubmit }: { onSubmit: (fullName: FullNameBody) => void } = $props()
  let fullName = $state<FullNameBody>({ firstName: '', lastName: '' })
</script>

<form class="grid gap-4 grid-cols-[min-content_1fr] items-center">
  <div class="text-lg font-bold col-span-2">{m('ENTER_BIO')}</div>
  <label class="text-nowrap" for="NewEmployeeDialog.firstName">{m('FIRST_NAME')}</label>
  <input class="input" id="NewEmployeeDialog.firstName" autocomplete="off" bind:value={fullName.firstName} />
  <label class="text-nowrap" for="NewEmployeeDialog.lastName">{m('LAST_NAME')}</label>
  <input class="input" id="NewEmployeeDialog.lastName" autocomplete="off" bind:value={fullName.lastName} />
  <button type="submit" onclick={() => onSubmit(fullName)} class="btn btn-primary ml-auto col-span-2">
    {m('SAVE')}
  </button>
</form>
