import CommonConfig from "../../../entities/config/CommonConfig"
import Department from "../../../entities/Department"
import PluviometricPost from "../../../entities/PluviometricPost"

export default class PluviometricPostDTO {
  id: number
  label: string
  latitude: number
  longitude: number
  department: Department

  constructor(p: PluviometricPost, commonConfig: CommonConfig) {
    this.id = p.id
    this.label = p.label
    this.latitude = p.latitude
    this.longitude = p.longitude
    this.department = commonConfig.departments.get(p.departmentId)
  }
}
