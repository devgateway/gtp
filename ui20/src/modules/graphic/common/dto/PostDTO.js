import PluviometricPost from "../../../entities/PluviometricPost"
import DepartmentDTO from "./DepartmentDTO"

export default class PostDTO {
  id: number
  label: string
  latitude: number
  longitude: number
  departmentId: number
  department: DepartmentDTO

  constructor(post: PluviometricPost, deparmtnet: DepartmentDTO) {
    Object.assign(this, post)
    this.department = deparmtnet
  }
}
