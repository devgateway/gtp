import Product from "../Product"
import Quantity from "./Quantity"

export default class ProductQuantities {
  product: Product
  quantities: Array<Quantity>

  constructor(product: Product) {
    this.product = product
    this.quantities = []
  }

  addQuantity(quantity: Quantity) {
    this.quantities.push(quantity)
  }

  sortByDate() {
    this.quantities.sort(Quantity.compareByDate)
  }

}
