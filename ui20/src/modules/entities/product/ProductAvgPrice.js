export default class ProductAvgPrice {
  price: number
  priceTypeId: number

  constructor({price, priceTypeId}) {
    this.price = price
    this.priceTypeId = priceTypeId
  }
}
