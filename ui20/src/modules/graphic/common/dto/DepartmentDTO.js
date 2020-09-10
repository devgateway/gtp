import Department from "../../../entities/Department"
import RegionDTO from "./RegionDTO"

export default class DepartmentDTO {
  id: number
  name: string
  code: string
  regionId: number
  region: RegionDTO

  constructor(department: Department, region: RegionDTO) {
    Object.assign(this, department)
    this.region = region
  }
}
