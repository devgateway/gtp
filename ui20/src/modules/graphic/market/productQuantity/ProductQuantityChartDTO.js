import ProductType from "../../../entities/product/ProductType"
import ProductQuantityLine from "./ProductQuantityLine"

export default class ProductQuantityChartDTO {
  productType: ProductType
  lines: Array<ProductQuantityLine>

  constructor(productType: ProductType, lines: Array<ProductQuantityLine>) {
    this.productType = productType
    this.lines = lines
  }

}
