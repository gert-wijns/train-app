<script lang="ts">
  import type { TranslatableMessageParamResponse } from '$assets-api/models/TranslatableMessageParamResponse'
  import type { TranslatableMessageResponse } from '$assets-api/models/TranslatableMessageResponse'
  import { m } from '$lib/i18n/translate.svelte'
  import Icon from '@iconify/svelte'
  import TranslateableMessage from './TranslateableMessage.svelte'

  let props: { message: TranslatableMessageResponse } = $props()

  let translated = $derived(m(props.message.key as any, translateParams(props.message.params)))

  function translateParam(param: TranslatableMessageParamResponse) {
    switch (param.type) {
      case 'KEY':
        return m(param.value as any)
      default:
        return param.value
    }
  }
  function translateParams(params: TranslatableMessageParamResponse[]) {
    var translated: any = {}
    params.forEach(param => {
      translated[param.key] = translateParam(param)
    })
    return translated
  }
</script>

<div class="flex items-center gap-1">
  {#if props.message.severity === 'ERROR'}
    <Icon icon="material-symbols:error-rounded" class="text-error" />
  {:else if props.message.severity === 'WARN'}
    <Icon icon="material-symbols:warning-rounded" class="text-warning" />
  {:else}
    <Icon icon="material-symbols:info-rounded" class="text-info" />
  {/if}
  {translated}
</div>
<div class="ml-2">
  {#each props.message.subMessages as subMessage}
    <TranslateableMessage message={subMessage} />
  {/each}
</div>
