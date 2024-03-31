export interface Measure {
  lat_: number
  long_: number
  deparment_: string
  time_: {
    seconds_: number
    nanos_: number
    memoizedIsInitialized: number
    unknownFields: {
      fields: any
    }
    memoizedSize: number
    memoizedHashCode: number
  }
  measure_: string
  value_: number
  memoizedIsInitialized: number
  unknownFields: {
    fields: any
  }
  memoizedSize: number
  memoizedHashCode: number
}

export interface MeasuresState {
  measures: Measure[]
  connected: boolean
}
