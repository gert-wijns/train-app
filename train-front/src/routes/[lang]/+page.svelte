<script>
  import { route } from '$lib/ROUTES'
  import { page } from '$app/state'
  import { m } from '$lib/i18n/translate.svelte'
</script>

<div class="grid gap-5 w-4xl text-lg">
  <div class="text-2xl font-bold">Menu</div>

  <div class="flex gap-2">
    <a class="link hover:link-primary" href={route('/[lang]/assets', { lang: page.params.lang })}>{m('ASSETS')}</a>
    &#x2022;
    <a class="link hover:link-primary" href={route('/[lang]/assets/locomotives', { lang: page.params.lang })}>
      {m('LOCOMOTIVES')}
    </a>
    &#x2022;
    <a class="link hover:link-primary" href={route('/[lang]/assets/locomotivemodels', { lang: page.params.lang })}>
      {m('LOCOMOTIVE_MODELS')}
    </a>
    &#x2022;
    <a class="link hover:link-primary" href={route('/[lang]/assets/wagonmodels', { lang: page.params.lang })}>
      {m('WAGON_MODELS')}
    </a>
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
  <div class="flex gap-2">
    <div>{m('PLANNING')}</div>
    &#x2022;
    <a class="link hover:link-primary" href={route('/[lang]/planning/trains', { lang: page.params.lang })}>
      {m('TRAINS')}
    </a>
    &#x2022;
    <a class="link hover:link-primary" href={route('/[lang]/planning/train-engineers', { lang: page.params.lang })}>
      {m('TRAIN_ENGINEERS')}
    </a>
  </div>
</div>
