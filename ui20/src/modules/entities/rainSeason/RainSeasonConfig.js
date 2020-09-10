import CommonConfig from "../config/CommonConfig"
import Department from "../Department"
import PluviometricPost from "../PluviometricPost"
import Region from "../Region"
import Zone from "../Zone"

export default class RainSeasonConfig {
  years: Array<number>
  zones: Map<number, Zone>
  regions: Map<number, Region>
  departments: Map<number, Department>
  posts: Map<number, PluviometricPost>

  static from(years: Array<number>, posts: Array<PluviometricPost>, commonConfig: CommonConfig) {
    const config = new RainSeasonConfig()
    config.years = years
    config.posts = posts.reduce((map:Map, p) => map.set(p.id, p), new Map())
    config.departments = posts.map(p => commonConfig.departments.get(p.departmentId))
      .reduce((map:Map, d) => map.set(d.id, d), new Map())
    config.regions = Array.from(config.departments.values()).map(d => commonConfig.regions.get(d.regionId))
      .reduce((map:Map, r) => map.set(r.id, r), new Map())
    config.zones = Array.from(config.regions.values()).map(r => commonConfig.zones.get(r.zoneId))
      .reduce((map:Map, z) => map.set(z.id, z), new Map())
    return config
  }
}
