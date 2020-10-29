import RainMapLayers from "./RainMapLayers"
import * as rainfallMapCss from "./rainfallMapCss"

export default class CumulativeRainMapLayers extends RainMapLayers {

  constructor(polyline, polygon) {
    super(polyline, polygon, colorsFunc)
  }
}

// TODO color detection
const colorsFunc = (zlevel, index) => {
  return rainfallMapCss.cumulColors[index]
}
