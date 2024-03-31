// for departments vvvvv
// sets the popup html for the department
// depending on the show_stats parameter
export const getHTMLDepartment = async (
  store: any,
  show_stats: string,
  properties: any,
): Promise<HTMLDivElement> => {
  const popup = document.createElement('div')
  popup.classList.add('popup')
  popup.style.setProperty('width', `220px`)
  popup.style.setProperty('height', `150px`)
  let bottomBody = ''
  if (show_stats === 'show') {
    let total = {} as any
    let count = {} as any
    const { measures } = storeToRefs(store)
    for (let measure of measures.value) {
      if (measure.department_ === properties.nombre) {
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
      veloc = `<p><strong>Velocidad:</strong> ${toFixed(total.speed / count.speed, 2)}km/h</p>`
    }
    let parked = ''
    if (total.parked !== undefined) {
      parked = `<p><strong>Nro. Estacionados:</strong> ${toFixed(total.parked / count.parked, 2)}</p>`
    }
    let accidents = ''
    if (total.accidents !== undefined) {
      accidents = `<p><strong>Nro. Accidentes:</strong> ${toFixed(total.accidents / count.accidents, 2)}</p>`
    }
    let cars = ''
    if (total.cars !== undefined) {
      cars = `<p><strong>Nro. Autos:</strong> ${toFixed(total.cars / count.cars, 2)}</p>`
    }
    let trucks = ''
    if (total.trucks !== undefined) {
      trucks = `<p><strong>Nro. Camiones:</strong> ${toFixed(total.trucks / count.trucks, 2)}</p>`
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
            ${measuresDiv}
            ${veloc}
            ${parked}
            ${accidents}
            ${cars}
            ${trucks}
    `
  } else {
    let centroide = JSON.parse(properties.centroide)
    let provincia = properties.provincia
      ? JSON.parse(properties.provincia).nombre
      : 'Buenos Aires'
    bottomBody = `
            <p><strong>Centroide (lat/lon):</strong> ${centroide.lat.toFixed(2)}/${centroide.lon.toFixed(2)}</p>
            <p><strong>Nombre completo:</strong> ${properties.nombre_completo}</p>
            <p><strong> Categoria:</strong> ${properties.categoria}</p>
            <p><strong> Provincia:</strong> ${provincia}</p>
    `
  }
  let html = `
    <div>
        <div class="popupBody">
          <div class="popupTop">
            ${properties.nombre}
          </div>
          <div class="bodyDepartment">
          ${bottomBody}
          </div>
        </div>
    </div>
  `

  popup.innerHTML = html
  return popup
}

const toFixed = (num: number, fixed: number): string => {
  return num.toFixed(fixed)
}

// for markers vvvvv
// sets the popup html for the measure
export const getHTMLPopup = (measure: Measure): HTMLDivElement => {
  let date = new Date(measure.time_.seconds_ * 1000 - 3 * 60 * 60 * 1000)

  const popup = document.createElement('div')
  popup.classList.add('popup')
  popup.style.setProperty('width', `220px`)
  popup.style.setProperty('height', `150px`)

  let windDiv = `
    <div
      class="popupTime"
    >
      ${date.toLocaleDateString('es-AR')}
    </div>
    `

  let precipDiv = `
    <div
      class="popupLatLon"
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
              <div class="popupMeasure">${measure.value_}</div>
              ${windDiv}
            </div>

            <div class="popupBottomRight">
              ${precipDiv}
              <img class="popupIcon" src="${getImg(measure.measure_)}" alt="MapTiler logo" />
              <div class="popupTime justifyRight">
              ${date.toLocaleTimeString('es-AR')}
              </div>
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
      return '/speed-alt.svg'
    case 'parked':
      return '/parked-car.svg'
    case 'accidents':
      return '/car-crash.svg'
    case 'cars':
      return '/car.svg'
    case 'trucks':
      return '/truck.svg'
    default:
      return '/car.svg'
  }
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
