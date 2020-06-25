import Product from "../../../entities/product/Product"
import ProductAvgPrice from "../../../entities/product/price/ProductAvgPrice"
import ProductPriceLine from "./ProductPriceLine"

export default class ProductPriceChartDTO {
  product: Product
  lines: Array<ProductPriceLine>
  previousYearAverages: Array<ProductAvgPrice>

  constructor(product: Product, lines: Array<ProductPriceLine>, previousYearAverages: Array<ProductAvgPrice>) {
    this.product = product
    this.lines = lines
    this.previousYearAverages = previousYearAverages
  }

}
