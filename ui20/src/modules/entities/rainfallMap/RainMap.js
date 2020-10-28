import RainMapConfig from "./RainMapConfig"
import RainMapData from "./RainMapData"
import RainMapFilter from "./RainMapFilter"
import RainMapSetting from "./RainMapSetting"

const RainMap: {
  config: RainMapConfig,
  filter: RainMapFilter,
  setting: RainMapSetting,
  data: RainMapData,
} = {
  filter: RainMapFilter,
  setting: RainMapSetting,
  data: RainMapData,
}

export const rainMapFromApi: RainMap = ({config, filter}) => {
  RainMap.config = new RainMapConfig(config)
  RainMap.filter.year = filter.year
  RainMap.filter.month = filter.month
  RainMap.filter.decadal = filter.decadal
  return RainMap
}

export default RainMap
