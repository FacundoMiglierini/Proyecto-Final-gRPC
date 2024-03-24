<template>
  <nav class="inline-block overflow-x-auto">
    <v-btn-toggle
      rounded="0"
      class="inline-block overflow-x-auto"
      color="deep-purple-accent-3"
      v-model="toggle_exclusive"
    >
      <v-btn>Marcadores</v-btn>
      <v-btn>Departamentos</v-btn>
    </v-btn-toggle>
    <v-btn-toggle
      v-if="toggle_exclusive === 1"
      v-model="show_stats"
      color="deep-purple-accent-3"
      class="inline-block overflow-x-auto"
      rounded="0"
      group
    >
      <v-btn class="inline-block" value="show">Mostrar Stats</v-btn>
      <v-btn class="inline-block" value="hide">Ocultar Stats</v-btn>
    </v-btn-toggle>
    <v-btn-toggle
      v-if="toggle_exclusive === 0"
      v-model="velocity"
      color="deep-purple-accent-3"
      class="inline-block overflow-x-auto"
      rounded="0"
      group
    >
      <v-btn class="inline-block" value="1"> 1 seg </v-btn>
      <v-btn class="inline-block" value="5"> 5 seg </v-btn>
      <v-btn class="inline-block" value="10"> 10 seg </v-btn>
      <v-btn class="inline-block" value="25"> 25 seg </v-btn>
    </v-btn-toggle>
    <v-btn v-if="toggle_exclusive === 0" @click="clearMarkers"
      >Limpiar marcadores</v-btn
    >
  </nav>
  <div class="map-wrap">
    <div class="map" ref="mapContainer"></div>
  </div>
</template>

<script setup lang="ts">
import {
  Map,
  MapStyle,
  Marker,
  Popup,
  config,
  type MapOptions,
  type Offset,
} from '@maptiler/sdk'
import { shallowRef, onMounted, onUnmounted, markRaw } from 'vue'
import '@maptiler/sdk/dist/maptiler-sdk.css'
import rutas from '../public/rutas_provinciales_bsas.json'
import departamentos from '../public/departamentos_bsas.json'
import { storeToRefs } from 'pinia'
import type { Measure } from '~/interfaces/measures'

const props = defineProps<{
  store: any
}>()

const toggle_exclusive = ref(0)
const velocity = ref('5')
const show_stats = ref('hide')

const mapContainer = shallowRef<string>()
const map = shallowRef<Map>()
let markers = [] as Marker[]

onMounted(async () => {
  config.apiKey = 'Wl3umxiiNruPIDlZmf3v'

  const initialState = { lat: -37.255, lng: -60.39, zoom: 6 }

  map.value = markRaw(
    new Map({
      container: mapContainer.value ? mapContainer.value : '',
      style: MapStyle.STREETS,
      center: [initialState.lng, initialState.lat],
      zoom: initialState.zoom,
    }),
  )

  map.value.on('load', () => {
    if (map.value == undefined) return
    map.value.addSource('departamentos_bsas', {
      type: 'geojson',
      data: departamentos,
    })
    map.value.addLayer({
      id: 'departamentos_bsas',
      type: 'fill',
      source: 'departamentos_bsas',
      layout: {},
      paint: {
        'fill-color': '#088',
        'fill-opacity': 0.1,
        'fill-outline-color': '#0ff',
      },
    })

    map.value.addSource('rutas_provinciales_bsas', {
      type: 'geojson',
      data: rutas,
    })
    map.value.addLayer({
      id: 'rutas_provinciales_bsas',
      type: 'line',
      source: 'rutas_provinciales_bsas',
      layout: {},
      paint: {
        // orange primary color
        'line-color': '#a33',
        'line-width': 1,
      },
    })

    map.value.on('click', 'departamentos_bsas', (e) => {
      if (e.features == undefined || toggle_exclusive.value === 0) return
      let p = new Popup()
        .setLngLat(e.lngLat)
        .setHTML('<p style="color:white; background:black">Cargando...</p>')
        .addTo(map.value!!)
      getHTMLDepartment(e.features[0].properties).then((popupHTML) => {
        p.remove()
        new Popup()
          .setLngLat(e.lngLat)
          .setDOMContent(popupHTML)
          .addTo(map.value!!)
      })
    })

    props.store.$subscribe((data: any) => {
      if (data.type == 'patch function' && toggle_exclusive.value === 0) {
        const measure = data.events[0].newValue
        //@ts-ignore
        const p = new Popup()
          .setDOMContent(getHTMLPopup(measure))
          .setMaxWidth('300px')
        const m = new Marker({ color: '#f00', scale: 0.5 })
          .setLngLat([measure.long_, measure.lat_])
          .setPopup(p)
          .addTo(map.value!!)
        markers.push(m)

        setTimeout(
          () => {
            markers = markers.filter((marker) => marker !== m)
            m.remove()
          },
          parseInt(velocity.value) * 1000,
        )
      }
    })
  })
}),
  onUnmounted(() => {
    map.value?.remove()
  })

const getHTMLPopup = (measure: Measure): HTMLDivElement => {
  let date = new Date(measure.time_.seconds_ * 1000 - 3 * 60 * 60 * 1000)

  const popup = document.createElement('div')
  popup.classList.add('popup')
  popup.style.setProperty('width', `200px`)
  popup.style.setProperty('height', `100px`)

  let windDiv = `
    <div
      class="popupWind"
    >
      ${date.toLocaleDateString('es-AR')} | ${date.toLocaleTimeString('es-AR')}
    </div>
    `

  let precipDiv = `
    <div
      class="popupPrecipitation"
    >
      lat/lon: ${measure.lat_.toFixed(2)}/${measure.long_.toFixed(2)}
    </div>
    `
  let html = `
    <div>
        <div class="popupBody">
          <div class="popupTop">
            ${mapMeasureTitle(measure.measure_)}
          </div>

          <div class="popupBottom">

            <div class="popupBottomLeft">
              <div class="popupTemperature">${measure.value_}</div>
              ${windDiv}
            </div>

            <div class="popupBottomRight">
              <img class="popupMainWeatherIcon" src="${getImg(measure.measure_)}" alt="MapTiler logo" />
              ${precipDiv}
            </div>

          </div>
        </div>
    </div>
  `

  popup.innerHTML = html
  return popup
}

const getImg = (measure: string) => {
  switch (measure) {
    case 'speed':
      return 'https://www.svgrepo.com/show/458910/speed-alt.svg'
    case 'parked':
      return 'https://www.svgrepo.com/show/105016/parked-car.svg'
    case 'accidents':
      return 'https://www.svgrepo.com/show/334499/car-crash.svg'
    case 'cars':
      return 'https://www.svgrepo.com/show/25407/car.svg'
    case 'trucks':
      return 'https://www.svgrepo.com/show/481035/truck.svg'
    default:
      return 'https://www.svgrepo.com/show/25407/car.svg'
  }
}

const getHTMLDepartment = async (properties: any): Promise<HTMLDivElement> => {
  const popup = document.createElement('div')
  popup.classList.add('popup')
  popup.style.setProperty('width', `200px`)
  popup.style.setProperty('height', `100px`)
  let bottomBody = ''
  if (show_stats.value === 'show') {
    let total = {} as any
    let count = {} as any
    const { measures } = storeToRefs(props.store)
    for (let measure of measures.value) {
      // https://apis.datos.gob.ar/georef/api/ubicacion?lat=-27.2741&lon=-66.7529
      const res = await fetch(
        `https://apis.datos.gob.ar/georef/api/ubicacion?lat=${measure.lat_}&lon=${measure.long_}`,
        {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        },
      ).then((res) => res.json())
      if (res.ubicacion.departamento.id === properties.id) {
        console.log('measure', measure)
        if (!total[measure.measure_]) {
          total[measure.measure_] = 0
          count[measure.measure_] = 0
        }
        total[measure.measure_] += measure.value_
        count[measure.measure_] += 1
      }
    }

    let veloc = ''
    if (total.speed !== undefined) {
      veloc = `<p>Velocidad: ${total.speed / count.speed}km/h</p>`
    }
    let parked = ''
    if (total.parked !== undefined) {
      parked = `<p>Nro. Estacionados: ${total.parked / count.parked}</p>`
    }
    let accidents = ''
    if (total.accidents !== undefined) {
      accidents = `<p>Nro. Accidentes: ${total.accidents / count.accidents}</p>`
    }
    let cars = ''
    if (total.cars !== undefined) {
      cars = `<p>Nro. Autos: ${total.cars / count.cars}</p>`
    }
    let trucks = ''
    if (total.trucks !== undefined) {
      trucks = `<p>Nro. Camiones: ${total.trucks / count.trucks}</p>`
    }

    let measuresDiv = '<p>No hay mediciones para este departamento</p>'
    if (
      veloc !== '' ||
      parked !== '' ||
      accidents !== '' ||
      cars !== '' ||
      trucks !== ''
    ) {
      measuresDiv = `<p>Mediciones en promedio:</p>`
    }
    bottomBody = `
          <div>
            ${measuresDiv}
            ${veloc}
            ${parked}
            ${accidents}
            ${cars}
            ${trucks}
          </div>
    `
  } else {
    let centroide = JSON.parse(properties.centroide)
    let provincia = properties.provincia ? JSON.parse(properties.provincia).nombre : 'Buenos Aires'
    bottomBody = `
          <div>
            <p><strong>Centroide (lat/lon):</strong> ${centroide.lat.toFixed(2)}/${centroide.lon.toFixed(2)}</p>
            <p><strong>Nombre completo:</strong> ${properties.nombre_completo}</p>
            <p><strong> Categoria:</strong> ${properties.categoria}</p>
            <p><strong> Provincia:</strong> ${provincia}</p>
          </div>
    `
  }
  let html = `
    <div>
        <div class="popupBody">
          <div class="popupTop">
            ${properties.nombre}
          </div>
          ${bottomBody}
        </div>
    </div>
  `

  popup.innerHTML = html
  return popup
}

const clearMarkers = () => {
  markers.forEach((marker) => marker.remove())
  markers = []
}

const mapMeasureTitle = (measure: string) => {
  switch (measure) {
    case 'speed':
      return 'Velocidad'
    case 'parked':
      return 'Nro. Estacionados'
    case 'accidents':
      return 'Nro. Accidentes'
    case 'cars':
      return 'Nro. Autos'
    case 'trucks':
      return 'Nro. Camiones'
    default:
      return measure
  }
}
</script>

<style scoped>
.map-wrap {
  position: relative;
  width: 100%;
  height: calc(100vh - 77px);
  /* calculate height of the screen minus the heading */
}

.map {
  position: absolute;
  width: 100%;
  height: 100%;
}
</style>
