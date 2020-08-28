
const defaultFillColor = '#fff'
const defaultColor = '#000'

const colors = [
  // '#FFFFFF',
  '#FFFFCC',
  '#FFFF99',
  '#FFFF66',
  '#CCCC33',
  '#CCFFCC',
  '#99CC66',
  '#99CC33',
  '#669933',
  '#CCCCFF',
  '#9999FF',
  '#6699FF',
  '#6666FF',
  '#99FFFF',
  '#00CCFF',
  '#0000FF'
]

export const mapLevelToColor = ({features}) => {
  const levels = new Set()
  features.forEach(f => {
    const zlevel = f.properties.ZLEVEL || f.properties.ZLEVEL === 0
    if (zlevel) {
      levels.add(+zlevel)
    }
  })
  const levelsLists = Array.from(levels).sort((a, b) => a - b)
  return levelsLists.reduce((map: Map, l, index) => {
    map.set(l, colors[index])
    return map
  }, new Map())
}

/*
const featureColor = (level, isFill) => {
  // return colorByLevel[level] || (isFill ? defaultFillColor : defaultColor)
  if (!level) return '#fff'
  const levelIdx = Math.trunc(level / 100)
  console.log(`levelIdx=${levelIdx}`)
  return colorLevelPer100[levelIdx]
}
 */

export const getFeatureStyle = (colorsMap) => (feature) => {
  const {ZLEVEL} = feature.properties
  console.log(`feature.properties.zlevel=${ZLEVEL}`)
  const hasLevel = !!ZLEVEL || ZLEVEL === 0
  let color = colorsMap.get(ZLEVEL)
  console.log(`color=${color}`)
  if (!color) {
    console.log(`No color hasLevel=${hasLevel}`)
  }

  return {
    // the fillColor is adapted from a property which can be changed by the user (segment)
    fillColor: hasLevel ? color : "#fff",
    weight: hasLevel ? 0.5 : 0,
    // stroke-width: to have a constant width on the screen need to adapt with scale
    // opacity: hasLevel ? 1 : 0,
    opacity: 1,
    color: color,
    dashArray: '3',
    // fillOpacity: hasLevel ? 1 : 0,
    fillOpacity: 1,
  }
}

// geo json
export const cleanupFeatures = (json) => {
  console.log(`json.features.length=${json.features.length}`)
  json.features = json.features.filter(f => !!f.properties.ZLEVEL)
  console.log(`json.features.length=${json.features.length}`)
}

// topo json
export const cleanupFeatures2 = (json) => {
  console.log(`json.features.length=${json.objects.anomalie_23_aoutPoly.geometries.length}`)
  json.objects.anomalie_23_aoutPoly.geometries = json.objects.anomalie_23_aoutPoly.geometries.filter(f => !!f.properties.ZLEVEL)
  console.log(`json.features.length=${json.objects.anomalie_23_aoutPoly.geometries.length}`)
}
