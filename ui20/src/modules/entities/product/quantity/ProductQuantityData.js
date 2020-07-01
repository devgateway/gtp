import {getOrDefault} from "../../../utils/DataUtilis"
import MonthDay from "../../MonthDay"
import Product from "../Product"
import ProductQuantities from "./ProductQuantities"
import Quantity from "./Quantity"

export default class ProductQuantityData {
  quantitiesByProductId: Map<number, ProductQuantities>

  constructor({ quantities}, year: number, productsById: Map<number, Product>) {
    const newProductQuantities = (productId) => new ProductQuantities(productsById.get(productId))
    this.quantitiesByProductId = new Map();
    (quantities || []).forEach(({monthDay, quantity, productId}) => {
      const q = new Quantity(new MonthDay(monthDay, year), quantity)
      getOrDefault(this.quantitiesByProductId, productId, null, () => newProductQuantities(productId)).addQuantity(q)
    })
    Array.from(this.quantitiesByProductId.values()).forEach((pqs: ProductQuantities) => pqs.sortByDate())
  }

}
