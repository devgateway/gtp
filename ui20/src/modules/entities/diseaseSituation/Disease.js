export default class Disease {
  id: number
  name: string
  label: string

  constructor({id, name, label}) {
    this.id = id
    this.name = name
    this.label = label
  }

  static localeCompare(d1: Disease, d2: Disease) {
    return d1.label.localeCompare(d2.label)
  }
}
