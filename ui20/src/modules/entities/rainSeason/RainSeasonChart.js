import * as C from "../../graphic/water/rainSeason/RainSeasonConstants"
import RainSeasonConfig from "./RainSeasonConfig"
import RainSeasonData from "./RainSeasonData"
import RainSeasonFilter from "./RainSeasonFilter"

const RainSeasonChart: {
  config: RainSeasonConfig,
  filter: RainSeasonFilter,
  data: RainSeasonData,
  sortedBy: string,
  sortedAsc: boolean,
} = {
  sortedBy: C.DEPARTMENT,
  sortedAsc: true,
}

export const rainSeasonChartFromApi: RainSeasonChart  = ({ config, filter, data }) => {
  RainSeasonChart.config = new RainSeasonConfig(config.years)
  RainSeasonChart.filter = RainSeasonFilter
  RainSeasonFilter.year = filter.year
  RainSeasonChart.data = new RainSeasonData(data, filter.year)
  return RainSeasonChart
}

export default RainSeasonChart
