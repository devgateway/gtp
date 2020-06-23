import Department from "../Department"
import MarketType from "./MarketType"

export default class Market {
  id: number
  name: string
  marketDays
  latitude: number
  longitude: number
  departmentId: number
  department: Department
  typeId: number
  type: MarketType

  constructor(props, departments: Map<number, Department>, marketTypes: Map<number, MarketType>) {
    Object.assign(this, props)
    this.department = departments.get(this.departmentId)
    this.type = marketTypes.get(this.typeId)
  }

  static localeCompare(m1: Market, m2: Market) {
    return m1.name.localeCompare(m2.name)
  }
}
