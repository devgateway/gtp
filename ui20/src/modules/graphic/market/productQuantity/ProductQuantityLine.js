import Product from "../../../entities/product/Product"
import DateLine from "../../common/dto/DateLine"
import DateLinePoint from "../../common/dto/DateLinePoint"

export default class ProductQuantityLine extends DateLine {
  product: Product

  constructor(product: Product, points: Array<DateLinePoint>) {
    super(product.name, points)
    this.product = product
  }

}
