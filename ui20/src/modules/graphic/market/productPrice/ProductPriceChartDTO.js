import Product from "../../../entities/product/Product"
import DateLine from "../../common/dto/DateLine"

export default class ProductPriceChartDTO {
  product: Product
  lines: Array<DateLine>

  constructor(product: Product, lines: Array<DateLine>) {
    this.product = product
    this.lines = lines
  }

}
