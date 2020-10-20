import * as api from "../../../modules/api"
import AgricultureConfig from "../../../modules/entities/config/AgricultureConfig"
import CommonConfig from "../../../modules/entities/config/CommonConfig"
import {priceFromApi} from "../../../modules/entities/product/price/ProductPriceChart"
import {quantityFromApi} from "../../../modules/entities/product/quantity/ProductQuantityChart"
import {MARKET_AND_AGRICULTURE} from "../../reducers/Agriculture"
import {updateCommonConfig} from "../appActions"
import * as appActions from "../appActions"


export const loadAllMarketData = () => (dispatch, getState) => {
  dispatch({
    type: MARKET_AND_AGRICULTURE,
    payload: api.getAllMarketAndAgriculture().then(result =>
      transformAll(result, updateCommonConfig(result.commonConfig)(dispatch, getState)))
  })
  appActions.loadMapAttribution("topo")(dispatch, getState)
}

const transformAll = (allData, commonConfig: CommonConfig) => {
  const agricultureConfig = new AgricultureConfig(allData.agricultureConfig, commonConfig)
  return {
    agricultureConfig,
    productPriceChart: priceFromApi(allData.productPricesChart, agricultureConfig),
    productQuantityChart: quantityFromApi(allData.productQuantitiesChart, agricultureConfig),
  }
}
