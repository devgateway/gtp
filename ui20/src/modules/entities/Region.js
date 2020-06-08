import Zone from "./Zone"

export default class Region {
  id: number
  name: string
  code: string
  zoneId: number
  zone: Zone

  constructor(props) {
    Object.assign(this, props)
  }

  static newInstance(props) {
    return new Region(props)
  }

  static localeCompare(r1: Region, r2: Region) {
    return r1.name.localeCompare(r2.name)
  }
}
