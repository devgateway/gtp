import PriceType from "../../../entities/product/price/PriceType"
import DateLine from "../../common/dto/DateLine"
import DateLinePoint from "../../common/dto/DateLinePoint"

export default class ProductPriceLine extends DateLine {
  priceType: PriceType

  constructor(priceType: PriceType, points: Array<DateLinePoint>) {
    super(priceType.label, points)
    this.priceType = priceType
  }

}
