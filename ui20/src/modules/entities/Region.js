export default class Region {
  id
  label
  code
  zone

  constructor(props) {
    Object.assign(this, props)
  }

  static newInstance(props) {
    return new Region(props)
  }
}
