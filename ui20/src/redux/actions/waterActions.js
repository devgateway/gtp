import * as api from "../../modules/api/index";
import CommonConfig from "../../modules/entities/rainfall/CommonConfig";
import RainLevelChart from "../../modules/entities/rainfall/RainLevelChart";
import {WATER_RESOURCES} from "../reducers/Water";

export const loadAllWaterData = () => (dispatch, getState) => {
  dispatch({
    type: WATER_RESOURCES,
    payload: api.getAllWaterResources().then(transformAll)
  });
}

const transformAll = (allData) => {
  const {commonConfig, rainLevelChart} = allData
  return {
    commonConfig: new CommonConfig(commonConfig),
    rainLevelChart: new RainLevelChart(rainLevelChart)
  }
}
