export default class Zone {
  id: number
  label: string

  constructor(props) {
    Object.assign(this, props)
  }

  static newInstance(props) {
    return new Zone(props)
  }

  static localeCompare(z1: Zone, z2: Zone) {
    return z1.label.localeCompare(z2.label)
  }
}
