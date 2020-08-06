import ProductType from "../../../entities/product/ProductType"
import ProductQuantityLine from "./ProductQuantityLine"

export default class ProductQuantityChartDTO {
  productType: ProductType
  lines: Array<ProductQuantityLine>
  unit: string
  maxQuantity: number
  hasData: boolean

  constructor(productType: ProductType, lines: Array<ProductQuantityLine>, maxQuantity: number) {
    this.productType = productType
    this.lines = lines
    this.unit = productType.name === 'livestock' ? 'unités' : 'kg'
    this.maxQuantity = maxQuantity
    this.hasData = !!lines.length
  }

}
