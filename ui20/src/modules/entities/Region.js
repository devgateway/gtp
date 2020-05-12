import Zone from "./Zone"

export default class Region {
  id: number
  label: string
  code: string
  zoneId: number
  zone: Zone

  constructor(props) {
    Object.assign(this, props)
  }

  static newInstance(props) {
    return new Region(props)
  }
}
