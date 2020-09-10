import CommonConfig from "../../../entities/config/CommonConfig"
import WaterConfig from "../../../entities/config/WaterConfig"
import PluviometricPost from "../../../entities/PluviometricPost"
import RainSeasonConfig from "../../../entities/rainSeason/RainSeasonConfig"
import RainSeasonData from "../../../entities/rainSeason/RainSeasonData"
import RainSeasonFilter from "../../../entities/rainSeason/RainSeasonFilter"
import RainSeasonPrediction from "../../../entities/rainSeason/RainSeasonPrediction"
import PostDTO from "../../common/dto/PostDTO"
import Posts from "../../common/dto/Posts"

export default class RainSeasonFilterHandler {
  data: RainSeasonData
  filteredData: RainSeasonData
  fullConfig: RainSeasonConfig
  _filteredConfig: RainSeasonConfig
  actualConfig: RainSeasonConfig
  rainSeasonFilter: RainSeasonFilter
  waterConfig: WaterConfig
  commonConfig: CommonConfig
  zoneIds: Set<number>
  regionIds: Set<number>
  departmentIds: Set<number>
  postIds: Set<number>
  filteredPosts: Array<PluviometricPost>

  constructor(data: RainSeasonData, fullConfig: RainSeasonConfig, rainSeasonFilter: RainSeasonFilter,
    waterConfig: WaterConfig, commonConfig: CommonConfig) {
    this.data = data
    this.filteredData = data.clone()
    this.fullConfig = fullConfig
    this.rainSeasonFilter = rainSeasonFilter
    this.waterConfig = waterConfig
    this.commonConfig = commonConfig
    this.postDTOById = new Posts(Array.from(this.waterConfig.posts.values()), this.commonConfig).buildPostDTOById()

    this.zoneIds = new Set(rainSeasonFilter.zoneIds)
    this.regionIds = new Set(rainSeasonFilter.regionIds)
    this.departmentIds = new Set(rainSeasonFilter.departmentIds)
    this.postIds = new Set(rainSeasonFilter.postIds)
    this._filter = this._filter.bind(this)
  }

  applyFilter() {
    this.filteredData.predictions = this._filter(this.data.predictions)
    this.filteredPosts = this.filteredData.predictions.map(p => this.waterConfig.posts.get(p.pluviometricPostId))
    this._filteredConfig = RainSeasonConfig.from(this.fullConfig.years, this.filteredPosts, this.commonConfig)
    this.constructor.removeNotApplicableFilters(this.rainSeasonFilter, this._filteredConfig)
    this.actualConfig = this._getOptions(this.fullConfig, this._filteredConfig)
  }

  _filter(data: Array<RainSeasonPrediction>) {
    return data.filter(p => {

      const post: PostDTO = this.postDTOById.get(p.pluviometricPostId)

      return ((!this.postIds.size || this.postIds.has(post.id)) &&
        (!this.departmentIds.size || this.departmentIds.has(post.departmentId)) &&
        (!this.regionIds.size || this.regionIds.has(post.department.regionId)) &&
        (!this.zoneIds.size || this.zoneIds.has(post.department.region.zoneId)))
    })
  }

  static removeNotApplicableFilters(rainSeasonFilter: RainSeasonFilter, config: RainSeasonConfig) {
    const filter = (ids: Array<number>, options: Map) => ids.filter(id => options.has(id))
    rainSeasonFilter.postIds = filter(rainSeasonFilter.postIds, config.posts)
    rainSeasonFilter.departmentIds = filter(rainSeasonFilter.departmentIds, config.departments)
    rainSeasonFilter.regionIds = filter(rainSeasonFilter.regionIds, config.regions)
    rainSeasonFilter.zoneIds = filter(rainSeasonFilter.zoneIds, config.zones)
  }

  _getOptions(fullConfig: RainSeasonConfig, filteredConfig: RainSeasonConfig) {
    const filteredBy = new Set()
    this.zoneIds.size && filteredBy.add('zones');
    this.regionIds.size && filteredBy.add('regions');
    this.departmentIds.size && filteredBy.add('departments');
    this.postIds.size && filteredBy.add('posts');

    if (filteredBy.size > 1) {
      return filteredConfig
    } else if (filteredBy.size === 0) {
      return fullConfig
    }
    return Object.assign(new RainSeasonConfig(), {
      years: fullConfig.years,
      zones: filteredBy.has('zones') ? fullConfig.zones : filteredConfig.zones,
      regions: filteredBy.has('regions') ? fullConfig.regions : filteredConfig.regions,
      departments: filteredBy.has('departments') ? fullConfig.departments : filteredConfig.departments,
      posts: filteredBy.has('posts') ? fullConfig.posts : filteredConfig.posts,
    })
  }

}
