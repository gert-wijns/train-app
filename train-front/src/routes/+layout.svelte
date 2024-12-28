<script lang="ts">
  import '../app.css'
  import Toasts from '$lib/common/Toasts.svelte'

  let { children } = $props()
</script>

<Toasts />

{@render children()}

<style>
  :global(::-webkit-scrollbar) {
    background-color: white;
    width: 12px;
  }
  :global(::-webkit-scrollbar-track) {
    background-color: white;
  }
  :global(::-webkit-scrollbar-button) {
    display: none;
  }
  :global(::-webkit-scrollbar-thumb) {
    background-color: #a0a0a59d;
    border-radius: 15px;
    border: 2px solid white;
  }
  :global(::-webkit-scrollbar-thumb:hover) {
    background-color: #a0a0a5;
  }
  :global(body) {
    --scp-font-size: 14px;
    font-size: var(--scp-font-size);
  }
  :global(select) {
    background-color: rgb(241 245 249);
  }
</style>
