export default class GTPLocation {
  id: number
  name: String

  constructor({id, name} = {}) {
    this.id = id
    this.name = name
  }

  static localeCompare(l1: GTPLocation, l2: GTPLocation) {
    if (l1.id === null) return -1
    if (l2.id === null) return 1
    return l1.name.localeCompare(l2.name)
  }
}
