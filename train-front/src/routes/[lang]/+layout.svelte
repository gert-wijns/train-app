<script>
  import { route } from '$lib/ROUTES'
  import Icon from '@iconify/svelte'
</script>

<div class="navbar bg-base-200 shadow-sm">
  <div class="navbar-start">
    <a class="btn btn-ghost btn-circle" href={route('/[lang]', { lang: 'en' })} aria-label="menu">
      <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h7" />
      </svg>
    </a>
  </div>
  <div class="navbar-center"></div>
  <div class="navbar-end">
    <button class="btn btn-ghost btn-circle" aria-label="search">
      <Icon class="h-5 w-5" icon="tabler:search" />
    </button>
    <button class="btn btn-ghost btn-circle" aria-label="bell">
      <div class="indicator">
        <Icon class="h-5 w-5" icon="tabler:bell" />
        <span class="indicator-item text-sky-800 text-xs font-bold">
          <span class="animate-ping absolute inline-flex h-full w-full rounded-4xl bg-sky-400 opacity-25"></span>
          <span>3</span>
        </span>
      </div>
    </button>
  </div>
</div>

<div class="m-5">
  <slot></slot>
</div>
