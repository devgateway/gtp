export default class ProductType {
  id: number
  name: string
  label: string
  marketTypeId: number

  constructor({id, name, label, marketTypeId}) {
    this.id = id
    this.name = name
    this.label = label
    this.marketTypeId = marketTypeId
  }

  static localeCompare(pt1: ProductType, pt2: ProductType) {
    return pt1.label.localeCompare(pt2.label)
  }
}
