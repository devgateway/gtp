import CommonConfig from "../../../entities/rainfall/CommonConfig"
import RainSeasonChart from "../../../entities/rainSeason/RainSeasonChart"
import RainSeasonConfig from "../../../entities/rainSeason/RainSeasonConfig"
import RainSeasonConfigDTO from "./RainSeasonConfigDTO"
import {RainSeasonPredictionDTO} from "./RainSeasonPredictionDTO"
import * as C from './RainSeasonConstants'

export default class RainSeasonTableBuilder {
  rainSeasonChart: RainSeasonChart
  commonConfig: CommonConfig
  data: Array<RainSeasonPredictionDTO>
  config: RainSeasonConfig

  constructor(rainSeasonChart: RainSeasonChart, commonConfig: CommonConfig) {
    this.rainSeasonChart = rainSeasonChart
    this.commonConfig = commonConfig
  }

  build() {
    let data = this.rainSeasonChart.data.predictions

    const {sortedBy, sortedAsc} = this.rainSeasonChart
    const isDateSorting = C.ACTUAL === sortedBy || C.PLANNED === sortedBy

    if (isDateSorting) {
      data = data.sort((a, b) => a[sortedBy].date.getTime() - b[sortedBy].date.getTime())
    }
    data = data.map(p => new RainSeasonPredictionDTO(this.commonConfig.posts.get(p.pluviometricPostId), p))
    if (sortedBy) {
      if (!isDateSorting) {
        if (C.DIFFERENCE === sortedBy) {
          data = data.sort((a, b) => a[sortedBy] - b[sortedBy])
        } else {
          data = data.sort((a, b) => a[sortedBy].localeCompare(b[sortedBy]))
        }
      }
      if (!sortedAsc) {
        data = data.reverse()
      }
    }
    this.data = data
    this.config = new RainSeasonConfigDTO({
      years: this.rainSeasonChart.config.years
    })
  }
}

