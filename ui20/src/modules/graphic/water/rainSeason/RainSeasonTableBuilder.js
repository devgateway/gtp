import CommonConfig from "../../../entities/rainfall/CommonConfig"
import RainSeasonChart from "../../../entities/rainSeason/RainSeasonChart"
import RainSeasonConfig from "../../../entities/rainSeason/RainSeasonConfig"
import RainSeasonPrediction from "../../../entities/rainSeason/RainSeasonPrediction"
import RainSeasonConfigDTO from "./RainSeasonConfigDTO"
import * as C from './RainSeasonConstants'
import {RainSeasonPredictionDTO} from "./RainSeasonPredictionDTO"

export default class RainSeasonTableBuilder {
  rainSeasonChart: RainSeasonChart
  commonConfig: CommonConfig
  data: Array<RainSeasonPredictionDTO>
  config: RainSeasonConfig
  zoneIds: Set<number>
  regionIds: Set<number>
  departmentIds: Set<number>
  postIds: Set<number>

  constructor(rainSeasonChart: RainSeasonChart, commonConfig: CommonConfig) {
    this.rainSeasonChart = rainSeasonChart
    this.commonConfig = commonConfig
    this.zoneIds = new Set(rainSeasonChart.filter.zoneIds)
    this.regionIds = new Set(rainSeasonChart.filter.regionIds)
    this.departmentIds = new Set(rainSeasonChart.filter.departmentIds)
    this.postIds = new Set(rainSeasonChart.filter.postIds)
  }

  build() {
    let data = this._filter(this.rainSeasonChart.data.predictions)

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
    this._prepareConfig()
  }

  _filter(data: Array<RainSeasonPrediction>) {
    return data.filter(p => {
      const post = this.commonConfig.posts.get(p.pluviometricPostId)
      return ((!this.postIds.size || this.postIds.has(post.id)) &&
        (!this.departmentIds.size || this.departmentIds.has(post.departmentId)) &&
        (!this.regionIds.size || this.regionIds.has(post.department.regionId)) &&
        (!this.zoneIds.size || this.zoneIds.has(post.department.region.zoneId)))
    })
  }

  _prepareConfig() {
    const {years, zones, regions, departments, posts} = this.rainSeasonChart.config
    this.config = new RainSeasonConfigDTO({
      years,
      zones: Array.from(zones.values()),
      regions: Array.from(regions.values()),
      departments: Array.from(departments.values()),
      posts: Array.from(posts.values()),
    })
  }

}

