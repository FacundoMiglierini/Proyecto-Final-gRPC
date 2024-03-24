<template>
  <v-container class="text-center" id="dashboard">
    <span class="text-bold text-lg">Dashboard</span>
    <BasicChart :store="props.store" />
  </v-container>
</template>

<script setup lang="ts">
import type { ChartInfo, ChartPoint } from '~/interfaces/charts'
import type { Measure } from '~/interfaces/measures'
import { useMeasuresStore } from '~/store/measures'
let measuresPoints = {} as any

const props = defineProps<{
  store: ReturnType<typeof useMeasuresStore>
}>()

props.store.$subscribe((state) => {
  if (state.type !== 'patch function') return
  state.events.forEach((event) => {
    measuresPoints[event.newValue?.measure_].push(
      convertMeasureToChartPoint(event.newValue),
    )
    //@ts-ignore
    infoCharts[event.newValue?.measure_].data =
      measuresPoints[event.newValue?.measure_]
  })
})

const convertMeasureToChartPoint = (measure: Measure): ChartPoint => {
  return {
    x: measure.time_.seconds_,
    y: measure.value_,
  }
}
</script>

<style>
.text-lg {
  font-size: 2.5rem !important;
}
</style>
