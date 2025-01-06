<script lang="ts">
  import { route } from '$lib/ROUTES'
  import Icon from '@iconify/svelte'
  import { dev } from '$app/environment'
  import { applyRegisteredDevAutoFills } from '$lib/dev/DevAutofillHook.svelte'
  import { createKeydownListener } from '$lib/common/keyListener'
  import { onMount } from 'svelte'

  if (dev) {
    onMount(createKeydownListener('alt+a', () => applyRegisteredDevAutoFills()))
  }
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
    {#if dev}
      <span class="tooltip tooltip-primary tooltip-bottom">
        <button
          class="btn btn-ghost btn-circle"
          aria-label="auto-fill-toggle"
          onclick={() => applyRegisteredDevAutoFills()}>
          <Icon class="h-5 w-5" icon="game-icons:fairy-wand" />
        </button>
        <div class="tooltip-content grid">
          <span>Autofill on forms.</span>
          <span>Only available in dev/test.</span>
          <span>
            <kbd class="kbd">alt</kbd>
            +
            <kbd class="kbd">a</kbd>
            anywhere
          </span>
        </div>
      </span>
    {/if}
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
