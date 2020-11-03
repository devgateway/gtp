import {ANOMALY_POLYLINE_COLOR} from "../../../modules/graphic/water/rainfallMap/rainfallMapCss"

export const getRainFeatureStyle = (colorsMap: Map) => (feature) => {
  const {ZLEVEL} = feature.properties
  // console.log(`feature.properties.zlevel=${ZLEVEL}`)
  let color = colorsMap.get(ZLEVEL)
  // console.log(`color=${color}`)
  if (!color) {
    console.error(`No color matching color found for ZLEVEL=${ZLEVEL}. Fallback to default color.`)
  }

  return {
    fillColor: color,
    weight: 0.5,
    opacity: 1,
    color: color,
    fillOpacity: 1,
  }
}

export const getAnomalyPolylineFeatureStyle = (colorsMap: Map) => (feature) => {
  const style = getRainFeatureStyle(colorsMap)(feature);
  [80, 100, 120].forEach(level => {
    if (feature.properties.ZLEVEL === level) {
      highlightLine(style, ANOMALY_POLYLINE_COLOR[`${level}`])
    }
  })
  return style
}

const highlightLine = (style, color) => {
  style.color = color
  style.weight = 2
  style.dashArray = 3
}

export const hasZLevel = (feature) => feature.properties.ZLEVEL || feature.properties.ZLEVEL === 0

export const onEachPolygonFeature = (colorsMap: Map, unit: string) => (feature, layer) => {
  if (hasZLevel(feature)) {
    const color = colorsMap.get(feature.properties.ZLEVEL)
    layer.bindTooltip(`
      <div>
        <span style="background-color: ${color}">&nbsp;</span>
        <span>&nbsp;${feature.properties.ZLEVEL}${unit}</span>
      </div>`,
      {
        permanent: false,
        sticky: true,
        direction: "top",
        className: "rain-tooltip"
      });
  }
}
