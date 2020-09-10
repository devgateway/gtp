import MarketDTO from "./MarketDTO"

export default class MarketLocationMapDTO {
  markets: Array<MarketDTO>

  constructor(markets: Array<MarketDTO>) {
    this.markets = markets
  }
}
