import WaterConfig from "../../../entities/config/WaterConfig"
import RainSeasonChart from "../../../entities/rainSeason/RainSeasonChart"
import RainSeasonConfigDTO from "./RainSeasonConfigDTO"
import * as C from './RainSeasonConstants'
import {RainSeasonPredictionDTO} from "./RainSeasonPredictionDTO"
import RainSeasonTableDTO from "./RainSeasonTableDTO"

export default class RainSeasonTableBuilder {
  rainSeasonChart: RainSeasonChart
  waterConfig: WaterConfig


  constructor(rainSeasonChart: RainSeasonChart, waterConfig: WaterConfig) {
    this.rainSeasonChart = rainSeasonChart
    this.waterConfig = waterConfig
  }

  build(): RainSeasonTableDTO {
    let data = this.rainSeasonChart.filteredData.predictions

    const {sortedBy, sortedAsc} = this.rainSeasonChart
    const isDateSorting = C.ACTUAL === sortedBy || C.PLANNED === sortedBy

    if (isDateSorting) {
      data = data.sort((a, b) => a[sortedBy].date.getTime() - b[sortedBy].date.getTime())
    }
    data = data.map(p => new RainSeasonPredictionDTO(this.waterConfig.posts.get(p.pluviometricPostId), p))
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
    return new RainSeasonTableDTO(data, this._prepareConfig())
  }

  _prepareConfig() {
    const {years, zones, regions, departments, posts} = this.rainSeasonChart.actualConfig
    return new RainSeasonConfigDTO({
      years,
      zones: Array.from(zones.values()),
      regions: Array.from(regions.values()),
      departments: Array.from(departments.values()),
      posts: Array.from(posts.values()),
    })
  }

}

