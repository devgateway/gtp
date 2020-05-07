import * as api from "../../modules/api/index"
import CommonConfig from "../../modules/entities/rainfall/CommonConfig"
import RainfallChartBuilder from "../../modules/graphic/water/RainfallChartBuilder"
import RainLevelChart from "../../modules/entities/rainfall/RainLevelChart"
import {WATER_RESOURCES} from "../reducers/Water"

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

export const getRain = (intl) => (dispatch, getState) => {
  const {rainLevelChart} = getState().getIn(['water', 'data'])
  const rainfallDTO = new RainfallChartBuilder(rainLevelChart, intl)

  rainfallDTO.build()

  return {
    barData: rainfallDTO.barData,
    keys: rainfallDTO.keys.map(k => `${k}`),
    keysWithRefs: Array.from(rainfallDTO.keyReferenceLevels.keys()),
    groupMode: 'grouped',
    indexBy: rainfallDTO.indexBy,
    colors: {
      // TODO update based on mockup
      scheme: 'category10'
    },
    byDecadal: rainfallDTO.byDecadal,
    monthDecadal: rainfallDTO.monthDecadal,
  }
}
