<script lang="ts">
  import { m } from '$lib/i18n/translate.svelte'
  import { page } from '$app/state'
  import type { RouteId } from './$types'

  const routeId: RouteId = '/[lang]/assets/add'

  let { children } = $props()
</script>

<div class="grid gap-5">
  <div class="text-2xl font-bold">{m('ADD_ASSET')}</div>
  <div class="card shadow-lg border border-primary p-5 bg-base-100 max-w-xl ml-auto mr-auto">
    <div class="py-3 w-full grid">
      <ul class="steps ml-auto mr-auto">
        <li class="step px-5" class:step-primary={routeId === page.route.id}>{m('CHOOSE_ASSET_TYPE')}</li>
        <li class="step" class:step-primary={routeId !== page.route.id}>{m('CONFIGURE_ASSET')}</li>
      </ul>
    </div>
    {@render children()}
  </div>
</div>
