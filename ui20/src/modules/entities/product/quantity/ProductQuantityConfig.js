import AgricultureConfig from "../../config/AgricultureConfig"
import Market from "../../market/Market"
import MarketType from "../../market/MarketType"
import ProductType from "../ProductType"
import * as C from "../../Constants"


export default class ProductQuantityConfig {
  years: Array<number>
  markets: Array<Market>
  productTypes: Array<ProductType>

  constructor({years} = {}, agricultureConfig: AgricultureConfig) {
    this.years = years || []
    const ruralMarketTypeId = Array.from(agricultureConfig.marketTypes.values())
      .find((mt: MarketType) => mt.name === C.MARKET_TYPE_RURAL).id
    this.markets = agricultureConfig.markets.filter((m: Market) => m.typeId === ruralMarketTypeId)
      .sort(Market.localeCompare)
    this.productTypes = Array.from(agricultureConfig.productTypes.values())
      .filter((pt: ProductType) => pt.marketTypeId === ruralMarketTypeId)
      .sort(ProductType.localeCompare)
  }

}
