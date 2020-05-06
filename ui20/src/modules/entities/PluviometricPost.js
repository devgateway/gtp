export default class PluviometricPost {
  id
  label
  latitude
  longitude
  department

  constructor(props) {
    Object.assign(this, props)
  }

  static newInstance(props) {
    return new PluviometricPost(props)
  }
}
