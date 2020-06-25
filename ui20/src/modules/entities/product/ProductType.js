export default class ProductType {
  id: number
  name: string
  label: string

  constructor({id, name, label}) {
    this.id = id
    this.name = name
    this.label = label
  }

  static localeCompare(pt1: ProductType, pt2: ProductType) {
    return pt1.label.localeCompare(pt2.label)
  }
}
