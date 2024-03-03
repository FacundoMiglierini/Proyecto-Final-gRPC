import { defineNuxtModule } from '@nuxt/kit'
import http from 'http'
let sockjs = require('sockjs')

export default defineNuxtModule({
  setup(options, nuxt) {
    nuxt.hook('listen', (server) => {
      console.log('Socket listen', server.address(), server.eventNames())
      //const io = new Server(server)
      // make server allowing cors
      //const option = {
      //  disable_cors: true,
      //}
      const io = sockjs.createServer()

      nuxt.hook('close', () => io.close())

      io.on('connection', (conn: any) => {
        console.log('connected', conn)
        conn.on('data', (message: any) => {
          conn.write(message)
        })
        conn.on('close', () => {})
      })
      //io.installHandlers(server, { prefix: '/' })
      const sv = http.createServer()
      io.installHandlers(sv, { prefix: '/' })
      sv.listen(60200)
    })
  },
})
