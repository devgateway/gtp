import Market from "../market/Market"
import MarketType from "../market/MarketType"
import CommonConfig from "./CommonConfig"

export default class AgricultureConfig extends CommonConfig {
  marketTypes: Map<number, MarketType>
  markets: Map<number, Market>

  constructor({marketTypes, markets}, commonConfig) {
    super(commonConfig)
    this.marketTypes = marketTypes.reduce((map, mt) => map.set(mt.id, new MarketType(mt)), new Map())
    this.markets = markets.map(m => new Market(m, this.departments, this.marketTypes))
  }

}
