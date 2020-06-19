import {getOrDefaultArray} from "../../utils/DataUtilis"
import Market from "../market/Market"
import MarketType from "../market/MarketType"
import PriceType from "../product/PriceType"
import Product from "../product/Product"
import ProductType from "../product/ProductType"
import CommonConfig from "./CommonConfig"

export default class AgricultureConfig extends CommonConfig {
  marketTypes: Map<number, MarketType>
  marketsById: Map<number, Market>
  markets: Array<Market>
  productTypes: Map<number, ProductType>
  priceTypes: Map<number, PriceType>
  productsById: Map<Number, Product>
  productsByTypeId: Map<number, Array<Product>>

  constructor({marketTypes, markets, productTypes, priceTypes, products}, commonConfig) {
    super(commonConfig)
    this.marketTypes = (marketTypes || []).reduce((map, mt) => map.set(mt.id, new MarketType(mt)), new Map())
    this.marketsById = (markets || []).reduce((map, m) => map.set(m.id, new Market(m, this.departments, this.marketTypes)), new Map())
    this.markets = Array.from(this.marketsById.values())
    this.productTypes = (productTypes || []).reduce((map, pt) => map.set(pt.id, new ProductType(pt)), new Map())
    this.priceTypes = (priceTypes || []).reduce((map, pt) => map.set(pt.id, new PriceType(pt)), new Map())

    products = (products || []).map(p => new Product(p)).sort(Product.localeCompare)
    this.productsById = products.reduce((map, p) => map.set(p.id, p), new Map())
    this.productsByTypeId = products.reduce((map, p) => {
      getOrDefaultArray(map, p.id).push(p)
      return map
    }, new Map())
  }

}
