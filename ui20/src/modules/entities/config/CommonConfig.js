import Department from "../Department"
import Region from "../Region"
import Zone from "../Zone"

const newWithParent = (loc, funcNew, parentProperty, parentSource: Map) => {
  const o = funcNew(loc)
  if (parentProperty) {
    o[parentProperty] = parentSource.get(o[`${parentProperty}Id`])
  }
  return o
}
export const fromApiToMap = (locality: Array = [], funcNew, parentProperty, parentSource) =>
  locality.reduce((map, loc) => map.set(loc.id, newWithParent(loc, funcNew, parentProperty, parentSource)), new Map())

export const reduceToMap = (locality: Array = [], funcNew) =>
  locality.reduce((map, loc) => map.set(loc.id, funcNew(loc)), new Map())


export default class CommonConfig {
  departments: Map<number, Department>
  regions: Map<number, Region>
  zones: Map<number, Zone>

  constructor({departments, regions, zones}) {
    this.zones = reduceToMap(zones, Zone.newInstance)
    this.regions = reduceToMap(regions, Region.newInstance)
    this.departments = reduceToMap(departments, Department.newInstance)
  }
}
