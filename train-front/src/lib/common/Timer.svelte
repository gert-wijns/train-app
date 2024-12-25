<script lang="ts">
  let { start, end }: { start: Date; end: Date | undefined } = $props();

  const startValue = +start;
  let elapsed = $state(+(end ?? new Date()) - startValue);

  $effect(() => {
    if (end === undefined) {
      const i = setInterval(() => {
        elapsed = +new Date() - startValue;
      }, 150);
      return () => {
        clearInterval(i);
      };
    }
  });
</script>

<span class="text-secondary text-xs">
  {#if elapsed > 1000}
    {Math.round(elapsed / 1000)}s {elapsed % 1000} ms
  {:else}
    {elapsed} ms
  {/if}
</span>
