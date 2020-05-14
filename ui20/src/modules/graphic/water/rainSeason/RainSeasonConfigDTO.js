import Department from "../../../entities/Department"
import PluviometricPost from "../../../entities/PluviometricPost"
import Region from "../../../entities/Region"
import Zone from "../../../entities/Zone"

export default class RainSeasonConfigDTO {
  years: Array<object>
  zones: Array<object>
  regions: Array<object>
  departments: Array<object>
  posts: Array<object>

  constructor({ years, zones, regions, departments, posts }) {
    this._setYears(years)
    this._setZones(zones)
    this._setRegions(regions)
    this._setDepartments(departments)
    this._setPosts(posts)
  }

  _setYears(years) {
    this.years = years.sort().reverse().map(y => ({key: y, value: y, text: y}))
  }

  _setZones(zones: Array<Zone>) {
    this.zones = zones.sort(Zone.localeCompare).map(z => ({key: z.id, value: z.id, text: z.label}))
  }

  _setRegions(regions: Array<Region>) {
    this.regions = regions.sort(Region.localeCompare).map(r => ({key: r.id, value: r.id, text: r.label}))
  }

  _setDepartments(departments: Array<Department>) {
    this.departments = departments.sort(Department.localeCompare).map(d => ({key: d.id, value: d.id, text: d.name}))
  }

  _setPosts(posts: Array<PluviometricPost>) {
    this.posts = posts.sort(PluviometricPost.localeCompare).map(p => ({key: p.id, value: p.id, text: p.label}))
  }
}
