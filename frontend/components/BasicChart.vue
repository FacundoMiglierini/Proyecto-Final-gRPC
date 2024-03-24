<template>
  <div class="row">
    <div class="form-group mx-auto">
      <label>{{otro}}a</label>
    </div>
    <highchart
      :options="chartOptions"
      :modules="['exporting']"
      :update="watchers"
      style="width:100%;"
    />
  </div>
</template>

<script lang="ts">


export default {
  props: {
    store: Object
  },
  data () {
    return {
      caption: 'Chart caption here',
      subtitle: 'a',
      points: [10, 0, 8, 2, 6, 4, 5, 5],
      seriesColor: '',
      otro: this.test,
      animationDuration: 1000,
      chartType: '',
      colorInputIsSupported: null,
      chartTypes: [],
      durations: [0, 500, 1000, 2000],
      seriesName: 'b',
      yAxis: 'My Values',
      watchers: [
        'options.title',
        'options.series'
      ],
      colors: [
        'Red',
        'Green',
        'Blue',
        'Pink',
        'Orange',
        'Brown',
        'Black',
        'Purple'
      ],
      lastPointClicked: {
        timestamp: '',
        x: '',
        y: ''
      },
      sexy: false
    }
  },
  computed: {
    /** @returns {import('@/lib/types').ChartOptions} */
    chartOptions () {
      const ctx = this
      return {
        accessibility: { enabled: false },
        caption: {
          text: this.caption,
          style: {
            // @ts-ignore
            color: this.sexy ? this.invertedColor(0) : '#black'
          }
        },
        chart: {
          backgroundColor: this.sexy
            ? {
                linearGradient: { x1: 0, x2: 0, y1: 0, y2: 1 },
                stops: [
                  [0, this.seriesColor],
                  [0.5, '#ffffff'],
                  [1, this.seriesColor]
                ]
              }
            : '#ffffff',
          className: 'my-chart',
          type: this.chartType.toLowerCase()
        },
        plotOptions: {
          series: {
            cursor: 'pointer',
            point: {
              events: {
                click () {
                  ctx.$emit('pointClicked', this)
                }
              }
            }
          }
        },
        yAxis: [{
          title: {
            text: this.yAxis,
            style: {
              color: '#000000'
            }
          }
        }],
        title: {
          style: {
            // @ts-ignore
            color: this.sexy ? this.invertedColor(0) : '#black'
          },
          text: `${this.caption} ` +
            (this.lastPointClicked.timestamp !== ''
              ? `(Point clicked: ${this.lastPointClicked.timestamp})`
              : '')
        },
        subtitle: {
          style: {
            // @ts-ignore
            color: this.sexy ? this.invertedColor(0) : '#black'
          },
          text: `${this.subtitle}`
        },
        legend: {
          itemStyle: {
            // @ts-ignore
            color: this.sexy ? this.invertedColor(0) : '#black'
          }
        },
        series: [{
          type: 'line',
          name: this.seriesName,
          data: this.points,
          color: this.seriesColor
        }]
      }
    }
  }
}
</script>

<style scoped>

</style>
