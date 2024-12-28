<script lang="ts">
  import type { HTMLDialogAttributes } from 'svelte/elements'
  import Dialog from './Dialog.svelte'

  let props: { children: any; cancel?: () => void } & HTMLDialogAttributes = $props()

  let dialog = $state() as Dialog

  $effect(() => dialog.showModal())
</script>

<Dialog bind:this={dialog} {...props} />
