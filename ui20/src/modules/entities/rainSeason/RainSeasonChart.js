import RainSeasonConfigDTO from "../../graphic/water/rainSeason/RainSeasonConfigDTO"
import * as C from "../../graphic/water/rainSeason/RainSeasonConstants"
import RainSeasonFilterHandler from "../../graphic/water/rainSeason/RainSeasonFilterHandler"
import CommonConfig from "../config/CommonConfig"
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
  sortedBy: C.ZONE,
  sortedAsc: true,
}

export const rainSeasonChartFromApi: RainSeasonChart  = (waterConfig: WaterConfig, commonConfig: CommonConfig,
  {config, filter, data }) => {
  RainSeasonChart.filter = RainSeasonFilter
  RainSeasonFilter.yearIds = (filter && filter.year && [filter.year]) || []
  return rainSeasonDataFromApi(RainSeasonChart, waterConfig, commonConfig, config, data)
}

export const rainSeasonDataFromApi: RainSeasonChart = (rainSeasonChart: RainSeasonChart, waterConfig: WaterConfig,
  commonConfig: CommonConfig, config: RainSeasonConfig, data) => {
  rainSeasonChart.data = new RainSeasonData(data, rainSeasonChart.filter.yearIds)
  const posts = rainSeasonChart.data.predictions.map(p => waterConfig.posts.get(p.pluviometricPostId))
  rainSeasonChart.config = RainSeasonConfig.from(config.years, posts, commonConfig)
  RainSeasonFilterHandler.removeNotApplicableFilters(rainSeasonChart.filter, rainSeasonChart.config)
  return handleFilter(rainSeasonChart, waterConfig, commonConfig)
}

export const handleFilter: RainSeasonChart = (rainSeasonChart: RainSeasonChart, waterConfig: WaterConfig,
  commonConfig: CommonConfig) => {
  const { data, config, filter} = rainSeasonChart
  const filterHandler = new RainSeasonFilterHandler(data, config, filter, waterConfig, commonConfig)
  filterHandler.applyFilter()
  rainSeasonChart.filteredData = filterHandler.filteredData
  rainSeasonChart.actualConfig = filterHandler.actualConfig
  return rainSeasonChart
}

export default RainSeasonChart
