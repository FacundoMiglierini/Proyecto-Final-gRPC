<template>
  <div class="map-wrap">
    <div class="map" ref="mapContainer"></div>
  </div>
</template>

<script setup lang="ts">
import { Map, MapStyle, Marker, config } from '@maptiler/sdk'
import { shallowRef, onMounted, onUnmounted, markRaw } from 'vue'
import '@maptiler/sdk/dist/maptiler-sdk.css'
import rutas from '../public/rutas_provinciales_bsas.json'
//import localidades from '../public/localidades_bsas.json'
type Localidad = {
  id: string
  nombre: string
  fuente: string
  provincia: {
    id: string
    nombre: string
  }
  departamento: {
    id: string
    nombre: string
  }
  municipio: {
    id: string
    nombre: string
  }
  localidad_censal: {
    id: string
    nombre: string
  }
  categoria: string
  centroide: {
    lon: number
    lat: number
  }
}

const mapContainer = shallowRef<string>()
const map = shallowRef<Map>()

onMounted(async () => {
  config.apiKey = 'Wl3umxiiNruPIDlZmf3v'

  const initialState = { lat: -37.255, lng: -60.390, zoom: 6 }

  map.value = markRaw(
    new Map({
      container: mapContainer.value ? mapContainer.value : '',
      style: MapStyle.STREETS,
      center: [initialState.lng, initialState.lat],
      zoom: initialState.zoom,
    }),
  )

  new Marker({ color: '#ff0000' })
    .setLngLat([initialState.lng, initialState.lat])
    .addTo(map.value)
  map.value.on('load', () => {
    if (map.value == undefined) return
    map.value.addSource('red_vial_ign_ont_a_prov_view', {
      type: 'geojson',
      data: rutas,
    })
    map.value.addLayer({
      id: 'grand_teton',
      type: 'line',
      source: 'red_vial_ign_ont_a_prov_view',
      layout: {},
      paint: {
        'line-color': '#118',
        'line-width': 1,
      },
    })
    //const markers = [] as Marker[]
    //localidades.forEach((localidad: Localidad) => {
    //  const m = new Marker({ color: '#ff0000', scale: 0.5 })
    //    .setLngLat([localidad.centroide.lon, localidad.centroide.lat])
    //    .addTo(map.value!!)
    //  markers.push(m)
    //})
    //setTimeout(() => {
    //  markers.forEach((m) => {
    //    m.remove()
    //  })
    //}, 5000)

  })
}),
  onUnmounted(() => {
    map.value?.remove()
  })
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
