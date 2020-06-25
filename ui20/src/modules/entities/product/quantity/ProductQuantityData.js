import {getOrDefault} from "../../../utils/DataUtilis"
import MonthDay from "../../MonthDay"
import Product from "../Product"
import ProductQuantities from "./ProductQuantities"
import Quantity from "./Quantity"

export default class ProductQuantityData {
  quantitiesByProductTypeId: Map<number, ProductQuantities>

  constructor({ quantities}, year: number, productsById: Map<number, Product>) {
    const newProductQuantities = (productId) => new ProductQuantities(productsById.get(productId))
    this.quantitiesByProductTypeId = new Map();
    (quantities || []).forEach(({monthDay, quantity, productId}) => {
      const q = new Quantity(new MonthDay(monthDay, year), quantity)
      getOrDefault(this.quantitiesByProductTypeId, productId, null, () => newProductQuantities(productId)).addQuantity(q)
    })
  }

}
