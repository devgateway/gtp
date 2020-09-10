export default class PluviometricPost {
  id: number
  label: string
  latitude: number
  longitude: number
  departmentId: number

  constructor(props) {
    Object.assign(this, props)
  }

  static newInstance(props) {
    return new PluviometricPost(props)
  }

  static localeCompare(p1: PluviometricPost, p2: PluviometricPost) {
    return p1.label.localeCompare(p2.label)
  }
}
