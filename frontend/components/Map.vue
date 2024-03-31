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
import { Map, MapStyle, Marker, Popup, config } from '@maptiler/sdk'
import { shallowRef, onMounted, onUnmounted, markRaw } from 'vue'
import '@maptiler/sdk/dist/maptiler-sdk.css'
import rutas from '../public/rutas_provinciales_bsas.json'
import departamentos from '../public/departamentos_bsas.json'
import { getHTMLDepartment, getHTMLPopup } from './popups'

const props = defineProps<{
  store: any
}>()

const toggle_exclusive = ref(0)
const velocity = ref('5')
const show_stats = ref('show')

const mapContainer = shallowRef<string>()
const map = shallowRef<Map>()
let markers = [] as Marker[]

onMounted(async () => {
  config.apiKey = 'Wl3umxiiNruPIDlZmf3v'

  // center of Buenos Aires
  const initialState = { lat: -37.255, lng: -60.39, zoom: 6 }

  map.value = markRaw(
    new Map({
      container: mapContainer.value ? mapContainer.value : '',
      style: MapStyle.STREETS,
      center: [initialState.lng, initialState.lat],
      zoom: initialState.zoom,
    }),
  )

  // adds polygons of departments of Buenos Aires
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

    // adds lines of Buenos Aires' provincial routes
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
        'line-color': '#a33',
        'line-width': 1,
      },
    })

    // sets click event for departments
    map.value.on('click', 'departamentos_bsas', (e) => {
      if (e.features == undefined || toggle_exclusive.value === 0) return
      let p = new Popup()
        .setLngLat(e.lngLat)
        .setHTML('<p style="color:white; background:black">Cargando...</p>')
        .addTo(map.value!!)
      getHTMLDepartment(
        props.store,
        show_stats.value,
        e.features[0].properties,
      ).then((popupHTML) => {
        p.remove()
        new Popup()
          .setLngLat(e.lngLat)
          .setDOMContent(popupHTML)
          .addTo(map.value!!)
      })
    })

    // listen for new measures to update the map with markers
    props.store.$subscribe((data: any) => {
      if (data.type == 'patch function' && toggle_exclusive.value === 0) {
        const measure = data?.events[0]?.newValue
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
})

onUnmounted(() => {
  map.value?.remove()
})

const clearMarkers = () => {
  markers.forEach((marker) => marker.remove())
  markers = []
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
