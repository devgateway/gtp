import * as api from "../../../modules/api"
import AgricultureConfig from "../../../modules/entities/config/AgricultureConfig"
import {MARKET_AND_AGRICULTURE} from "../../reducers/Agriculture"
import * as appActions from "../appActions"


export const loadAllMarketData = () => (dispatch, getState) => {
  dispatch({
    type: MARKET_AND_AGRICULTURE,
    payload: api.getAllMarketAndAgriculture().then(transformAll)
  })
  appActions.loadWorldMapAttribution()(dispatch, getState)
}

const transformAll = (allData) => {
  const agricultureConfig = new AgricultureConfig(allData.agricultureConfig, allData.commonConfig)
  return {
    agricultureConfig,
  }
}
