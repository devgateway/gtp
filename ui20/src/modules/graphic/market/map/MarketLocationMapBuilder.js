import AgricultureConfig from "../../../entities/config/AgricultureConfig"
import CommonConfig from "../../../entities/config/CommonConfig"
import MarketDTO from "./MarketDTO"
import MarketLocationMapDTO from "./MarketLocationMapDTO"

export default class MarketLocationMapBuilder {
  commonConfig: CommonConfig
  agricultureConfig: AgricultureConfig

  constructor(commonConfig: CommonConfig, agricultureConfig: AgricultureConfig) {
    this.commonConfig = commonConfig
    this.agricultureConfig = agricultureConfig
  }

  build(): MarketLocationMapDTO {
    const {markets} = this.agricultureConfig
    return new MarketLocationMapDTO(markets.map(m => new MarketDTO(m, this.commonConfig, this.agricultureConfig)))
  }
}
