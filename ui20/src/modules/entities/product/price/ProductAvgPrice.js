import PriceType from "./PriceType"

export default class ProductAvgPrice {
  average: number
  priceTypeId: number
  priceType: PriceType

  constructor({average, priceTypeId}, priceTypes: Map<number, PriceType>) {
    this.average = average
    this.priceTypeId = priceTypeId
    this.priceType = priceTypeId && priceTypes.get(priceTypeId)
  }
}
