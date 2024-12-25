<script lang="ts">
  import { m } from '$lib/i18n/translate.svelte'

  let { value }: { value: string } = $props()
</script>

{m(value as any)}
