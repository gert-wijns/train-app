<script lang="ts">
  import { goto } from '$app/navigation'
  import { page } from '$app/state'
  import OpenDialog from '$lib/dialog/OpenDialog.svelte'
  import { m } from '$lib/i18n/translate.svelte'
  import { route } from '$lib/ROUTES'

  let { children } = $props()

  const cancel = () => {
    goto(route('/[lang]/employees', { lang: page.params.lang }), { invalidateAll: true })
  }
</script>

<div class="card shadow-lg border border-primary p-5 bg-base-100 max-w-lg ml-auto mr-auto">
  <div class="py-3 w-full grid">
    <ul class="steps ml-auto mr-auto">
      <li class="step px-5" class:step-primary={page.params.employeeId === undefined}>{m('ENTER_BIO')}</li>
      <li class="step" class:step-primary={page.params.employeeId !== undefined}>{m('ASSIGN_ROLE')}</li>
    </ul>
  </div>
  {@render children()}
</div>
