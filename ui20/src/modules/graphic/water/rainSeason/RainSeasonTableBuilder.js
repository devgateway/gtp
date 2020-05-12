import CommonConfig from "../../../entities/rainfall/CommonConfig"
import RainSeasonChart from "../../../entities/rainSeason/RainSeasonChart"
import {RainSeasonPredictionDTO} from "./RainSeasonPredictionDTO"

export default class RainSeasonTableBuilder {
  rainSeasonChart: RainSeasonChart
  commonConfig: CommonConfig
  data: Array<RainSeasonPredictionDTO>

  constructor(rainSeasonChart: RainSeasonChart, commonConfig: CommonConfig) {
    this.rainSeasonChart = rainSeasonChart
    this.commonConfig = commonConfig
  }

  build() {
    this.data = this.rainSeasonChart.data.predictions
      .map(p => new RainSeasonPredictionDTO(this.commonConfig.posts.get(p.pluviometricPostId), p))
  }
}

