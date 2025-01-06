<script lang="ts">
  import { goto } from '$app/navigation'
  import { page } from '$app/state'
  import AddLocomotiveForm from '$lib/components/assets/AddLocomotiveForm.svelte'
  import { m } from '$lib/i18n/translate.svelte'
  import { route } from '$lib/ROUTES'

  const redirectToLocomotives = async () => {
    goto(route('/[lang]/assets/locomotives', { lang: page.params.lang }))
  }
</script>

<div class="grid gap-5">
  <div class="text-2xl font-bold">{m('ADD_ASSET')}</div>
  <div class="card shadow-lg border border-primary p-5 bg-base-100 max-w-xl ml-auto mr-auto">
    <AddLocomotiveForm onSuccesfullSubmit={redirectToLocomotives} />
  </div>
</div>
