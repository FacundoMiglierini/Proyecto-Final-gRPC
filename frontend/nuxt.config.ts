import vuetify, { transformAssetUrls } from 'vite-plugin-vuetify'
import { Server } from 'socket.io'
export default defineNuxtConfig({
  //...
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
  ],
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
  routeRules: {
    '/**': {
      cors: true,
    }
  }
})
