<template>
  <v-btn @click="goToDashboard">Ir al Dashboard</v-btn>
  <Map :store="store" />
  <Dashboard :store="store" />
</template>

<script setup lang="ts">
import { useMeasuresStore } from '~/store/measures'
const { $io } = useNuxtApp()
const store = useMeasuresStore()
onMounted(() => {
  $io.on('measure', (message: string) => {
    const data: Measure = JSON.parse(message)
    store.$patch((state: MeasuresState) => {
      state.measures.push(data)
    })

  })

})

const goToDashboard = () => {
  // go to end of the page
  document.getElementById('dashboard')?.scrollIntoView({ behavior: 'smooth' })
}
</script>
