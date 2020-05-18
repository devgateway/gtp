import RainSeasonConfigDTO from "../../graphic/water/rainSeason/RainSeasonConfigDTO"
import * as C from "../../graphic/water/rainSeason/RainSeasonConstants"
import RainSeasonFilterHandler from "../../graphic/water/rainSeason/RainSeasonFilterHandler"
import CommonConfig from "../rainfall/CommonConfig"
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

export const rainSeasonChartFromApi: RainSeasonChart  = (commonConfig: CommonConfig, {config, filter, data }) => {
  RainSeasonChart.filter = RainSeasonFilter
  RainSeasonFilter.yearIds = [filter.year]
  return rainSeasonDataFromApi(RainSeasonChart, commonConfig, config, data)
}

export const rainSeasonDataFromApi: RainSeasonChart = (rainSeasonChart: RainSeasonChart, commonConfig: CommonConfig,
  config: RainSeasonConfig, data) => {
  rainSeasonChart.data = new RainSeasonData(data, rainSeasonChart.filter.yearIds)
  const posts = rainSeasonChart.data.predictions.map(p => commonConfig.posts.get(p.pluviometricPostId))
  rainSeasonChart.config = RainSeasonConfig.from(config.years, posts)
  return handleFilter(rainSeasonChart, commonConfig)
}

export const handleFilter: RainSeasonChart = (rainSeasonChart: RainSeasonChart, commonConfig: CommonConfig) => {
  const { data, config, filter} = rainSeasonChart
  const filterHandler = new RainSeasonFilterHandler(data, config, filter, commonConfig)
  filterHandler.applyFilter()
  rainSeasonChart.filteredData = filterHandler.filteredData
  rainSeasonChart.actualConfig = filterHandler.actualConfig
  return rainSeasonChart
}

export default RainSeasonChart
