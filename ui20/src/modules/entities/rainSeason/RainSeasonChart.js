import * as C from "../../graphic/water/rainSeason/RainSeasonConstants"
import CommonConfig from "../rainfall/CommonConfig"
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

export const rainSeasonChartFromApi: RainSeasonChart  = (commonConfig: CommonConfig, {config, filter, data }) => {
  RainSeasonChart.filter = RainSeasonFilter
  RainSeasonFilter.yearIds = [filter.year]
  return rainSeasonDataFromApi(RainSeasonChart, commonConfig, config, data)
}

export const rainSeasonDataFromApi: RainSeasonChart = (rainSeasonChart: RainSeasonChart, commonConfig: CommonConfig,
  config, data) => {
  rainSeasonChart.data = new RainSeasonData(data, rainSeasonChart.filter.yearIds)
  const posts = rainSeasonChart.data.predictions.map(p => commonConfig.posts.get(p.pluviometricPostId))
  rainSeasonChart.config = new RainSeasonConfig(config.years, posts)
  return rainSeasonChart
}

export default RainSeasonChart
