export default class PriceType {
  id: number
  name: string
  label: string

  constructor({id, name, label}) {
    this.id = id
    this.name = name
    this.label = label
  }

  static localeCompare(pt1: PriceType, pt2: PriceType) {
    return pt1.label.localeCompare(pt2.label)
  }
}
