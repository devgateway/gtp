import {apiMonthDayAwareCompare, getOrDefaultArray} from "../../utils/DataUtilis"
import ProductAvgPrice from "./ProductAvgPrice"
import ProductPrice from "./ProductPrice"

export default class ProductPriceData {
  pricesByPriceTypeId: Map<number, Array<ProductPrice>>
  previousYearAverages: Array<ProductAvgPrice>

  constructor({prices, previousYearAverages} = {}, year) {
    this.pricesByPriceTypeId = (prices || []).sort(apiMonthDayAwareCompare)
      .map(p => new ProductPrice(p, year)).reduce((map, p) => {
      getOrDefaultArray(map, p.priceTypeId).push(p)
      return map
    }, new Map())
    this.previousYearAverages = (previousYearAverages || []).sort(apiMonthDayAwareCompare)
      .map(pa => new ProductAvgPrice(pa))
  }

}
