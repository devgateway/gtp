import ProductType from "../../../entities/product/ProductType"
import ProductQuantityLine from "./ProductQuantityLine"

export default class ProductQuantityChartDTO {
  productType: ProductType
  lines: Array<ProductQuantityLine>
  unit: string

  constructor(productType: ProductType, lines: Array<ProductQuantityLine>) {
    this.productType = productType
    this.lines = lines
    this.unit = productType.name === 'livestock' ? 'unités' : 'kg'
  }

}
