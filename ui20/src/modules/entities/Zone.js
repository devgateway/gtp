export default class Zone {
  id: number
  name: string

  constructor(props) {
    Object.assign(this, props)
  }

  static newInstance(props) {
    return new Zone(props)
  }

  static localeCompare(z1: Zone, z2: Zone) {
    return z1.name.localeCompare(z2.name)
  }
}
