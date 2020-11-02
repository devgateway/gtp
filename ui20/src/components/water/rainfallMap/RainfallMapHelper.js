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
    dashArray: '3',
    fillOpacity: 1,
  }
}
