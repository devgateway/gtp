export default class Product {
  id: number
  name: string
  unit: string
  productTypeId: number
  priceTypeIds: Array<number>

  constructor({ id, name, unit, productTypeId, priceTypeIds }) {
    this.id = id
    this.name = name
    this.unit = unit
    this.productTypeId = productTypeId
    this.priceTypeIds = priceTypeIds
  }

  static localeCompare(p1: Product, p2: Product) {
    return p1.name.localeCompare(p2.name)
  }

}
