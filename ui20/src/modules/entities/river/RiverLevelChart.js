import RiverLevelConfig from "./RiverLevelConfig"
import RiverLevelData from "./RiverLevelData"
import RiverLevelFilter from "./RiverLevelFilter"

const RiverLevelChart: {
  config: RiverLevelConfig,
  filter: RiverLevelFilter,
  data: RiverLevelData
} = {
}

export const riverLevelFromApi: RiverLevelChart = ({config, filter, data}) => {
  RiverLevelChart.config = new RiverLevelConfig(config)
  RiverLevelChart.filter = RiverLevelFilter
  RiverLevelChart.filter.riverStationId = filter.riverStationId
  RiverLevelChart.filter.years = filter.years
  RiverLevelChart.data = new RiverLevelData(data)
  return RiverLevelChart
}

export default RiverLevelChart
