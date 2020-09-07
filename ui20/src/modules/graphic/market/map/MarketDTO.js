import AgricultureConfig from "../../../entities/config/AgricultureConfig"
import Department from "../../../entities/Department"
import Market from "../../../entities/market/Market"
import MarketType from "../../../entities/market/MarketType"

export default class MarketDTO {
  id: number
  name: string
  marketDays
  latitude: number
  longitude: number
  department: Department
  type: MarketType

  constructor(m: Market, commonConfig: CommonConfig, agricultureConfig: AgricultureConfig) {
    this.id = m.id
    this.name = m.name
    this.marketDays = m.marketDays
    this.latitude = m.latitude
    this.longitude = m.longitude
    this.department = commonConfig.departments.get(m.departmentId)
    this.type = agricultureConfig.marketTypes.get(m.typeId)
  }

}
