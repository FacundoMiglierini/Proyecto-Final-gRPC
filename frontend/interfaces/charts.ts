export interface ChartInfo {
  title: string
  description: string
  data: ChartPoint[]
  measure: string
}

export interface ChartPoint {
  x: number
  y: number
}
