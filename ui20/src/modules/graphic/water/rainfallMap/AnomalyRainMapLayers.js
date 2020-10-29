import * as rainfallMapCss from "./rainfallMapCss"
import RainMapLayers from "./RainMapLayers"

export default class AnomalyRainMapLayers extends RainMapLayers {

  constructor(polyline, polygon) {
    super(polyline, polygon, colorsFunc)
  }
}

const colorsFunc = (zlevel, index) => {
  const level = Math.min(zlevel, rainfallMapCss.anomalyMaxLevel)
  const maxLevelIndex = rainfallMapCss.anomalyColors.findIndex(([l,]) => level <= l)
  return rainfallMapCss.anomalyColors[maxLevelIndex][1]
}
