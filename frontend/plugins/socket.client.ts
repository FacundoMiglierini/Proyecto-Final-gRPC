import io from 'socket.io-client'
import { useMeasuresStore } from '~/store/measures'

export default defineNuxtPlugin(() => {
  const socket = io('http://localhost:60200')
  const store = useMeasuresStore()

  socket.on('connect', () => {
    store.$patch({ connected: true })
    console.log('Connected')
  })
  socket.on('disconnect', () => {
    store.$patch({ connected: false })
    console.log('Disconnected')
  })
  socket.connect()

  return {
    provide: {
      io: socket,
    },
  }
})
