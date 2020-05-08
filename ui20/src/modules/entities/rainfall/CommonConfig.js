import Department from "../Department"
import PluviometricPost from "../PluviometricPost"
import Region from "../Region"
import Zone from "../Zone"

const fromApiToMap = (locality: Array, funcNew) => locality.reduce((map, p) => map.set(p.id, funcNew(p)), new Map())

export default class CommonConfig {
  posts: Map<number, PluviometricPost>
  departments: Map<number, Department>
  regions: Map<number, Region>
  zones: Map<number, Zone>

  constructor({ posts, departments, regions, zones}) {
    this.posts = fromApiToMap(posts, PluviometricPost.newInstance)
    this.departments = fromApiToMap(departments, Department.newInstance)
    this.regions = fromApiToMap(regions, Region.newInstance)
    this.zones = fromApiToMap(zones, Zone.newInstance)
  }
}
