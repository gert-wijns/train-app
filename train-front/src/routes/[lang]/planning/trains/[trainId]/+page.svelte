<script lang="ts">
  import { m } from '$lib/i18n/translate.svelte'
  import Icon from '@iconify/svelte'
  import type { LoadData } from './+page'
  import type { TrainLocomotiveResponse } from '$planning-api'

  let { data }: { data: LoadData } = $props()
  let locomotive: TrainLocomotiveResponse = $derived(data.train.locomotive)
</script>

<div class="grid gap-5">
  <div class="text-2xl font-bold">{m('TRAIN_DETAILS')}</div>

  <div class="text-2xl font-bold">{m('CREW')}</div>
  <div class="grid gap-4 grid-cols-[min-content_1fr]">
    <Icon class="w-12 h-12" icon="healthicons:truck-driver" />
    <div class="shadow-lg p-2 max-w-96 bg-base-200 grid grid-cols-2">
      <span>{m('BADGE_NR')}</span>
      <div>{locomotive.serialNumber}</div>
    </div>
  </div>

  <div class="text-2xl font-bold">{m('CONFIGURATION')}</div>
  <div class="grid gap-4 grid-cols-[min-content_1fr]">
    <Icon class="w-12 h-12" rotate={3} icon="wi:train" />
    <div class="shadow-lg p-2 max-w-96 bg-base-200 grid grid-cols-2">
      <span>{m('SERIAL_NUMBER')}</span>
      <div>{locomotive.serialNumber}</div>
    </div>
    {#each data.train.wagons as wagon}
      <Icon class="w-12 h-12" rotate={3} icon="mdi:train-car-passenger" />
      <div class="shadow-lg p-2 max-w-96 bg-base-200 grid grid-cols-2">
        <span>{m('SERIAL_NUMBER')}</span>
        <div>{wagon.serialNumber}</div>
      </div>
    {/each}
  </div>
</div>
