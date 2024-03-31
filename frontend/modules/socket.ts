import { defineNuxtModule } from '@nuxt/kit'
import { Server } from 'socket.io'

export default defineNuxtModule({
  setup(options, nuxt) {
    nuxt.hook('listen', (server) => {
      console.log('Socket listen', server.address(), server.eventNames())
      const io = new Server(60200, {
        cors: {
          origin: '*',
        },
      })
      nuxt.hook('close', () => io.close())

      io.on('connection', (socket: any) => {
        console.log('connected')
        socket.on('message', (message: any) => {
          socket.broadcast.emit('measure', message)
        })
        socket.on('close', () => {
          console.log('disconnected')
        })
      })
    })
  },
})
