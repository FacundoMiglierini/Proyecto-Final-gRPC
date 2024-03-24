import vuetify, { transformAssetUrls } from 'vite-plugin-vuetify'
export default defineNuxtConfig({
  build: {
    transpile: ['vuetify'],
  },
  modules: [
    (_options, nuxt) => {
      nuxt.hooks.hook('vite:extendConfig', (config) => {
        // @ts-expect-error
        config.plugins.push(vuetify({ autoImport: true }))
      })
    },
    'nuxt-socket-io',
    '@pinia/nuxt'
  ],
  // setup socketio socket
  io: {
    sockets: [
      {
        default: true,
        name: 'main',
        url: 'http://localhost:5000',
      },
    ],
  },
  vite: {
    vue: {
      template: {
        transformAssetUrls,
      },
    },
  },
  devtools: {
    enabled: true,
  },
  app: {
    pageTransition: {
      name: 'page',
      mode: 'out-in',
    },
  },
  devServer: {
    port: 5000,
  },
  imports: {
    dirs: ['interfaces/*.ts'],
  },
  css: ['~/assets/css/popup.css'],
})
