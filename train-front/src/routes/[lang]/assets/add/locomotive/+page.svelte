<script lang="ts">
  import { goto } from '$app/navigation'
  import { page } from '$app/state'
  import AddLocomotiveForm from '$lib/components/assets/AddLocomotiveForm.svelte'
  import { route } from '$lib/ROUTES'

  const redirectToAssets = async () => {
    goto(route('/[lang]/assets', { lang: page.params.lang }))
  }
</script>

<AddLocomotiveForm onSuccesfullSubmit={redirectToAssets} />
