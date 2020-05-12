import Region from "./Region"

export default class Department {
  id: number
  name: string
  code: string
  regionId: number
  region: Region

  constructor(props) {
    Object.assign(this, props)
  }

  static newInstance(props) {
    return new Department(props)
  }
}
