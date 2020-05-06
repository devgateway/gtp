export default class Department {
  id
  name
  code
  region

  constructor(props) {
    Object.assign(this, props)
  }

  static newInstance(props) {
    return new Department(props)
  }
}
