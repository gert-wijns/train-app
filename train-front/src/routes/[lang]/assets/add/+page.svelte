<script lang="ts">
  import Icon from '@iconify/svelte'
  import { m } from '$lib/i18n/translate.svelte'
</script>

<div class="grid gap-5">
  <div class="text-2xl font-bold">{m('ADD_ASSET')}</div>
  <div class="flex gap-5">
    <div class="card shadow p-3 hover:bg-base-300 hover:cursor-pointer border border-primary">
      <div class="card-title ml-auto mr-auto">{m('LOCOMOTIVE')}</div>
      <div class="card-body">
        <Icon width="120" height="120" hFlip icon="wi:train" />
      </div>
    </div>
    <div class="card shadow p-3 hover:bg-base-300 hover:cursor-pointer border border-primary">
      <div class="card-title ml-auto mr-auto">{m('WAGON')}</div>
      <div class="card-body">
        <Icon width="120" height="120" icon="mdi:train-car-passenger" />
      </div>
    </div>
  </div>
</div>
