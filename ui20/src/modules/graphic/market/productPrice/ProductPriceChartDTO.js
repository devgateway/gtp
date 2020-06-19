import Product from "../../../entities/product/Product"
import ProductAvgPrice from "../../../entities/product/ProductAvgPrice"
import DateLine from "../../common/dto/DateLine"

export default class ProductPriceChartDTO {
  product: Product
  lines: Array<DateLine>
  previousYearAverages: Array<ProductAvgPrice>

  constructor(product: Product, lines: Array<DateLine>, previousYearAverages: Array<ProductAvgPrice>) {
    this.product = product
    this.lines = lines
    this.previousYearAverages = previousYearAverages
  }

}
