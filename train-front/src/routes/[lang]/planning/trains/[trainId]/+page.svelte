<script lang="ts">
  import { M, m } from '$lib/i18n/translate.svelte'
  import Icon from '@iconify/svelte'
  import type { LoadData } from './+page'
  import type { TrainLocomotiveResponse } from '$planning-api'

  let { data }: { data: LoadData } = $props()
  let locomotive: TrainLocomotiveResponse = $derived(data.train.locomotive)
</script>

<div class="grid gap-5">
  <div class="text-2xl font-bold">{m('TRAIN_DETAILS')}</div>

  <div class="text-2xl font-bold">{m('CREW')}</div>
  <div class="grid gap-4 grid-cols-[min-content_1fr] items-center">
    <Icon class="w-12 h-12" icon="healthicons:truck-driver" />
    <div class="shadow-lg p-2 max-w-96 bg-base-200 grid grid-cols-2">
      <div class="col-span-2 text-sm font-bold">{m('TRAIN_ENGINEER')}</div>
      {#if data.train.trainEngineer}
        <span>{m('BADGE_NR')}</span>
        <div>{data.train.trainEngineer.id}</div>
      {:else}
        <span class="col-span-2">{m('NONE_BOARDED')}</span>
      {/if}
    </div>
  </div>

  <div class="text-2xl font-bold">{m('CONFIGURATION')}</div>
  <div class="grid gap-4 grid-cols-[min-content_1fr] items-center">
    <Icon class="w-12 h-12" rotate={3} icon="wi:train" />
    <div class="shadow-lg p-2 max-w-96 bg-base-200 grid grid-cols-2">
      <div class="col-span-2 text-sm font-bold flex gap-2 items-center">
        {m('LOCOMOTIVE')}
        {#if locomotive.decommissioned}
          <span class="badge badge-xs badge-error">
            {M('DECOMMISSIONED')}
          </span>
        {/if}
      </div>
      <span>{m('SERIAL_NUMBER')}</span>
      <div>{locomotive.serialNumber}</div>
    </div>
    {#each data.train.wagons as wagon}
      <Icon class="w-12 h-12" rotate={3} icon="mdi:train-car-passenger" />
      <div class="shadow-lg p-2 max-w-96 bg-base-200 grid grid-cols-2">
        <div class="col-span-2 text-sm font-bold">{m('WAGON')}</div>
        <span>{m('SERIAL_NUMBER')}</span>
        <div>{wagon.serialNumber}</div>
      </div>
    {/each}
  </div>
</div>
