<script lang="ts">
  import { page } from '$app/state'
  import TranslateableMessage from '$lib/common/TranslateableMessage.svelte'
  import { m } from '$lib/i18n/translate.svelte'
  const error: any | undefined = $derived(page.error)
  //$inspect(error)
</script>

{#if error?.message === 'insufficient_access'}
  <div>
    <div class="text-lg font-bold">{m('ACCESS_DENIED')}</div>
    <div class="m-4">
      <div>
        &#x2022; {m('MISSING_ROLE')}
        <b>{error?.role}</b>
      </div>
    </div>
  </div>
{:else if error?.key}
  <div class="grid gap-2 shadow-xl p-3 w-min text-nowrap rounded-xl border-base-300 border ml-auto mr-auto">
    <div class="text-lg font-bold">{m(('ERROR_STATUS_' + page.status) as any)}</div>
    <TranslateableMessage message={page.error as any} />
  </div>
{/if}
