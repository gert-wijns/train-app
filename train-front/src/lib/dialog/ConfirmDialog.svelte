<script lang="ts">
  let {
    title,
    message,
    onConfirm,
    dialogEl = $bindable(),
  }: {
    title: string
    message: string
    onConfirm: () => void
    dialogEl: HTMLDialogElement | undefined
  } = $props()
</script>

<dialog bind:this={dialogEl} class="modal">
  <div class="modal-box">
    <div class="card-body">
      <div class="card-title">{title}</div>
      {message}
      <div class="card-actions">
        <button class="btn btn-error ml-auto" onclick={() => onConfirm()}>Confirm</button>
      </div>
    </div>
  </div>
  <form method="dialog" class="modal-backdrop">
    <button>close</button>
  </form>
</dialog>
