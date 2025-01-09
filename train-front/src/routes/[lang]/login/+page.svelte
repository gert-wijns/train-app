<script>
  import { auth } from '$lib/Auth.svelte'
  import Form from '$lib/form/Form.svelte'
  import { TextFormField } from '$lib/form/FormField.svelte'
  import { FormModel } from '$lib/form/FormModel.svelte'
  import Input from '$lib/form/Input.svelte'
  import Label from '$lib/form/Label.svelte'
  import { m } from '$lib/i18n/translate.svelte'

  const username = new TextFormField().notBlank()
  const password = new TextFormField().notBlank()
  const formModel = new FormModel(
    'LoginForm',
    {
      username,
      password,
    },
    { submit },
  )

  async function submit() {
    auth.login(username.input, password.input)
    return true
  }
</script>

<div class="grid gap-5">
  <div class="card shadow-lg border border-primary p-5 bg-base-100 max-w-xl ml-auto mr-auto">
    <div class="py-3 w-full grid">
      {#if auth.isLoggedIn()}
        <div class="grid gap-2 min-w-lg items-center">
          <div class="text-lg font-bold col-span-2">{m('WELCOME_USERNAME', { username: auth.getUserId() })}</div>
          <div class="pb-4">{m('CURRENTLY_LOGGED_IN_GREETING')}</div>
          <button type="submit" class="btn btn-primary ml-auto col-span-2" onclick={() => auth.logout()}>
            {m('LOGOUT')}
          </button>
        </div>
      {:else}
        <Form {formModel} class="grid gap-2 grid-cols-[min-content_1fr] min-w-lg items-center">
          <div class="text-lg font-bold col-span-2">{m('LOGIN')}</div>
          <Label formField={username}>{m('USERNAME')}</Label>
          <Input formField={username} autocomplete="username" />
          <Label formField={password}>{m('PASSWORD')}</Label>
          <Input formField={password} autocomplete="current-password" type="password" />
          <button type="submit" class="btn btn-primary ml-auto col-span-2">
            {m('SAVE')}
          </button>
        </Form>
      {/if}
    </div>
  </div>
</div>
