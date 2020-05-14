import Department from "../Department"
import PluviometricPost from "../PluviometricPost"
import Region from "../Region"
import Zone from "../Zone"

export default class RainSeasonConfig {
  years: Array<number>
  zones: Set<Zone>
  regions: Set<Region>
  departments: Set<Department>
  posts: Set<PluviometricPost>

  constructor(years: Array<number>, posts: Array<PluviometricPost>) {
    this.years = years
    this.posts = new Set(posts)
    this.departments = new Set(posts.map(p => p.department))
    this.regions = new Set(posts.map(p => p.department.region))
    this.zones = new Set(posts.map(p => p.department.region.zone))
  }
}
