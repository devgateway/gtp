import RainSeasonConfigDTO from "../../graphic/water/rainSeason/RainSeasonConfigDTO"
import * as C from "../../graphic/water/rainSeason/RainSeasonConstants"
import RainSeasonFilterHandler from "../../graphic/water/rainSeason/RainSeasonFilterHandler"
import WaterConfig from "../config/WaterConfig"
import RainSeasonConfig from "./RainSeasonConfig"
import RainSeasonData from "./RainSeasonData"
import RainSeasonFilter from "./RainSeasonFilter"

const RainSeasonChart: {
  config: RainSeasonConfig,
  actualConfig: RainSeasonConfigDTO,
  filter: RainSeasonFilter,
  data: RainSeasonData,
  filteredData: RainSeasonData,
  sortedBy: string,
  sortedAsc: boolean,
} = {
  sortedBy: C.DEPARTMENT,
  sortedAsc: true,
}

export const rainSeasonChartFromApi: RainSeasonChart  = (waterConfig: WaterConfig, {config, filter, data }) => {
  RainSeasonChart.filter = RainSeasonFilter
  RainSeasonFilter.yearIds = [filter.year]
  return rainSeasonDataFromApi(RainSeasonChart, waterConfig, config, data)
}

export const rainSeasonDataFromApi: RainSeasonChart = (rainSeasonChart: RainSeasonChart, waterConfig: WaterConfig,
  config: RainSeasonConfig, data) => {
  rainSeasonChart.data = new RainSeasonData(data, rainSeasonChart.filter.yearIds)
  const posts = rainSeasonChart.data.predictions.map(p => waterConfig.posts.get(p.pluviometricPostId))
  rainSeasonChart.config = RainSeasonConfig.from(config.years, posts)
  RainSeasonFilterHandler.removeNotApplicableFilters(rainSeasonChart.filter, rainSeasonChart.config)
  return handleFilter(rainSeasonChart, waterConfig)
}

export const handleFilter: RainSeasonChart = (rainSeasonChart: RainSeasonChart, waterConfig: WaterConfig) => {
  const { data, config, filter} = rainSeasonChart
  const filterHandler = new RainSeasonFilterHandler(data, config, filter, waterConfig)
  filterHandler.applyFilter()
  rainSeasonChart.filteredData = filterHandler.filteredData
  rainSeasonChart.actualConfig = filterHandler.actualConfig
  return rainSeasonChart
}

export default RainSeasonChart
