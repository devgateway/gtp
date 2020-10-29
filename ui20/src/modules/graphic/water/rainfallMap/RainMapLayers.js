export default class RainMapLayers {
  polyline: Object
  polygon: Object
  colorsMap: Map

  constructor(polyline: Object, polygon: Object, colorsFunc: function) {
    this.polyline = polyline && cleanupFeatures(polyline)
    this.polygon = polygon && cleanupFeatures(polygon)
    this.colorsMap = this.polygon && mapZLevelToColor(colorsFunc, this.polygon)
  }
}

const cleanupFeatures = (geoJson) => {
  geoJson.features = geoJson.features.filter(f => (f.properties.ZLEVEL || f.properties.ZLEVEL === 0))
  return geoJson
}


const mapZLevelToColor = (colorsFunc, {features}) => {
  const levels = new Set()
  features.forEach(f => levels.add(+f.properties.ZLEVEL))

  const levelsLists = Array.from(levels).sort((a, b) => a - b)
  return levelsLists.reduce((map: Map, l, index) => {
    map.set(l, colorsFunc(l, index))
    return map
  }, new Map())
}
