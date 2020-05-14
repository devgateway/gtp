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

  constructor(years: Array<number>, posts: Array<PluviometricPost>) {
    this.years = years
    this.posts = posts.reduce((map:Map, p) => map.set(p.id, p), new Map())
    this.departments = posts.map(p => p.department).reduce((map:Map, d) => map.set(d.id, d), new Map())
    this.regions = Array.from(this.departments.values()).map(d => d.region)
      .reduce((map:Map, r) => map.set(r.id, r), new Map())
    this.zones = Array.from(this.regions.values()).map(r => r.zone)
      .reduce((map:Map, z) => map.set(z.id, z), new Map())
  }
}
