export default class Zone {
  id
  label

  constructor(props) {
    Object.assign(this, props)
  }

  static newInstance(props) {
    return new Zone(props)
  }
}
