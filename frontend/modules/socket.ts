import { defineNuxtModule } from '@nuxt/kit'
import http from 'http'
import { Server } from 'socket.io'

export default defineNuxtModule({
  setup(options, nuxt) {
    nuxt.hook('listen', (server) => {
      console.log('Socket listen', server.address(), server.eventNames())
      //const io = new Server(server)
      // make server allowing cors
      //const option = {
      //  disable_cors: true,
      //}
      const io = new Server(60200, {
        cors: {
          origin: '*',
        },
      })

      nuxt.hook('close', () => io.close())

      io.on('connection', (socket: any) => {
        console.log('connected')
        socket.on('message', (message: any) => {
          console.log('message', message)
          socket.write(message)
        })
        socket.on('close', () => {})
      })
    })
  },
})
