import RainMapLayers from "./RainMapLayers"
import * as rainfallMapCss from "./rainfallMapCss"

export default class CumulativeRainMapLayers extends RainMapLayers {

  constructor(polyline, polygon) {
    super(polyline, polygon, colorsFunc)
  }
}

// TODO color detection
const colorsFunc = (zlevel, index) => {
  if (index < rainfallMapCss.cumulColors.length) {
    return rainfallMapCss.cumulColors[index]
  }
  return rainfallMapCss.defaultCumulColor
}
