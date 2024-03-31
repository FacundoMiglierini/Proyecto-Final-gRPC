import { defineStore } from 'pinia'
import type { MeasuresState } from '~/interfaces/measures'

export const useMeasuresStore = defineStore('measures', {
  state: (): MeasuresState => {
    return {
      measures: [],
      connected: false,
    }
  },
})
