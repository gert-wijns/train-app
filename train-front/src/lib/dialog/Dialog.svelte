<script lang="ts">
  import type { HTMLDialogAttributes } from 'svelte/elements'

  let { children, cancel, ...rest }: { children: any; cancel?: () => void } & HTMLDialogAttributes = $props()

  let dialogEl = $state() as HTMLDialogElement
  $effect(() => {
    if (cancel) {
      dialogEl.addEventListener('cancel', () => cancel())
    }
  })

  export function showModal() {
    dialogEl.showModal()
  }
</script>

<dialog bind:this={dialogEl} class="modal" {...rest}>
  <div class="grid modal-box">
    {@render children()}
  </div>
  <form method="dialog" class="modal-backdrop">
    <button onclick={cancel}>close</button>
  </form>
</dialog>
