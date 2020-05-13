import Department from "./Department"

export default class PluviometricPost {
  id: number
  label: string
  latitude: number
  longitude: number
  departmentId: number
  department: Department

  constructor(props) {
    Object.assign(this, props)
  }

  static newInstance(props) {
    return new PluviometricPost(props)
  }
}
