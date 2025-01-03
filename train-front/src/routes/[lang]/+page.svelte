<script>
  import { route } from '$lib/ROUTES'
  import { page } from '$app/state'
  import { m } from '$lib/i18n/translate.svelte'
</script>

<div class="grid gap-5 w-4xl text-lg">
  <div class="text-2xl font-bold">Menu</div>

  <div>
    <a class="link hover:link-primary" href={route('/[lang]/assets', { lang: page.params.lang })}>{m('ASSETS')}</a>
  </div>
  <div>
    <a class="link hover:link-primary" href={route('/[lang]/employees', { lang: page.params.lang })}>
      {m('EMPLOYEES')}
    </a>
  </div>
  <div>
    <a class="link hover:link-primary" href={route('/[lang]/network', { lang: page.params.lang })}>
      {m('NETWORK')}
    </a>
  </div>
  <div>{m('PLANNING')}</div>
</div>
