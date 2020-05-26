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
}
