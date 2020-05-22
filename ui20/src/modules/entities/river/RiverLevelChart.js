import RiverLevelConfig from "./RiverLevelConfig"
import RiverLevelData from "./RiverLevelData"
import RiverLevelFilter from "./RiverLevelFilter"
import RiverLevelSettings from "./RiverLevelSettings"

const RiverLevelChart: {
  config: RiverLevelConfig,
  filter: RiverLevelFilter,
  setting: RiverLevelSettings,
  data: RiverLevelData
} = {
  filter: RiverLevelFilter,
  setting: RiverLevelSettings
}

export const riverLevelFromApi: RiverLevelChart = ({config, filter, data}) => {
  RiverLevelChart.config = new RiverLevelConfig(config)
  RiverLevelChart.filter.riverStationId = filter.riverStationId
  RiverLevelChart.filter.years = filter.years
  RiverLevelChart.data = new RiverLevelData(data)
  return RiverLevelChart
}

export default RiverLevelChart
