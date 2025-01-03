<script lang="ts">
  import Icon from '@iconify/svelte'
  import { m } from '$lib/i18n/translate.svelte'
  import { route } from '$lib/ROUTES'
  import { page } from '$app/state'
</script>

<div class="flex gap-5">
  <a
    href={route('/[lang]/assets/add/locomotive', { lang: page.params.lang })}
    class="card shadow p-2 hover:bg-base-300 border border-primary">
    <div class="card-title ml-auto mr-auto">{m('LOCOMOTIVE')}</div>
    <div class="card-body p-0">
      <Icon width="120" height="120" hFlip icon="wi:train" />
    </div>
  </a>
  <a
    href={route('/[lang]/assets/add/wagon', { lang: page.params.lang })}
    class="card shadow p-2 hover:bg-base-300 border border-primary">
    <div class="card-title ml-auto mr-auto">{m('WAGON')}</div>
    <div class="card-body p-0">
      <Icon width="120" height="120" icon="mdi:train-car-passenger" />
    </div>
  </a>
</div>
