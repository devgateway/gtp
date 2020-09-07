import MarketLocationMapBuilder from "../../../modules/graphic/market/map/MarketLocationMapBuilder"
import MarketLocationMapDTO from "../../../modules/graphic/market/map/MarketLocationMapDTO"

export const getMarketLocations = (): MarketLocationMapDTO => (dispatch, getState) => {
  const commonConfig = getState().getIn(['app', 'data', 'commonConfig'])
  const agricultureConfig = getState().getIn(['agriculture', 'data', 'agricultureConfig'])
  return {
    marketLocationsDTO: new MarketLocationMapBuilder(commonConfig, agricultureConfig).build()
  }
}
