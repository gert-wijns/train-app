<script lang="ts">
  import { page } from '$app/state'
  import { m } from '$lib/i18n/translate.svelte'

  let { children } = $props()
</script>

<div class="grid gap-5">
  <div class="text-2xl font-bold">{m('NEW_EMPLOYEE')}</div>
  <div class="card shadow-lg border border-primary p-5 bg-base-100 max-w-xl ml-auto mr-auto">
    <div class="py-3 w-full grid">
      <ul class="steps ml-auto mr-auto">
        <li class="step px-5" class:step-primary={page.params.employeeId === undefined}>{m('ENTER_BIO')}</li>
        <li class="step" class:step-primary={page.params.employeeId !== undefined}>{m('ASSIGN_ROLE')}</li>
      </ul>
    </div>
    {@render children()}
  </div>
</div>
