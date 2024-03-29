<template>
  <Map :store="store" />
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
</script>
