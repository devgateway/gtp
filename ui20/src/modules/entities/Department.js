export default class Department {
  id: number
  name: string
  code: string
  regionId: number

  constructor(props) {
    Object.assign(this, props)
  }

  static newInstance(props) {
    return new Department(props)
  }

  static localeCompare(d1: Department, d2: Department) {
    return d1.name.localeCompare(d2.name)
  }
}
