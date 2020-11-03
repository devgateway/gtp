import RainMapLayers, {getLevelsList} from "./RainMapLayers"
import * as rainfallMapCss from "./rainfallMapCss"

export default class CumulativeRainMapLayers extends RainMapLayers {

  constructor(polyline, polygon) {
    super(polyline, polygon, colorsFunc(getCumulColors(polygon)))
  }
}

const getCumulColors = ({features}) => {
  const levelsLists = getLevelsList({features})
  const palletBase = levelsLists[0] === 0 ? rainfallMapCss.cumulColors : rainfallMapCss.cumulColors.slice(1)
  let stepSize = (palletBase.length - 1) / (levelsLists.length - 1)
  let palletIndexes2 = levelsLists.map((l, idx) => Math.round(idx * stepSize))
  return palletIndexes2.map(idx => palletBase[idx])
}

const colorsFunc = (cumulColors) => (zlevel, index) => {
  index = index < cumulColors.length ? index : cumulColors.length - 1
  return cumulColors[index]
}
