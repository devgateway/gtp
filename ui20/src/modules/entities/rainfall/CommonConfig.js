import Department from "../Department"
import PluviometricPost from "../PluviometricPost"
import Region from "../Region"
import Zone from "../Zone"

const newWithParent = (loc, funcNew, parentProperty, parentSource: Map) => {
  const o = funcNew(loc)
  if (parentProperty) {
    o[parentProperty] = parentSource.get(o[`${parentProperty}Id`])
  }
  return o
}
const fromApiToMap = (locality: Array, funcNew, parentProperty, parentSource) =>
  locality.reduce((map, loc) => map.set(loc.id, newWithParent(loc, funcNew, parentProperty, parentSource)), new Map())

export default class CommonConfig {
  posts: Map<number, PluviometricPost>
  departments: Map<number, Department>
  regions: Map<number, Region>
  zones: Map<number, Zone>

  constructor({ posts }, {departments, regions, zones}) {
    this.zones = fromApiToMap(zones, Zone.newInstance)
    this.regions = fromApiToMap(regions, Region.newInstance, 'zone', this.zones)
    this.departments = fromApiToMap(departments, Department.newInstance, 'region', this.regions)
    this.posts = fromApiToMap(posts, PluviometricPost.newInstance, 'department', this.departments)
  }
}
