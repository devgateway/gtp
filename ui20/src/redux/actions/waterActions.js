import * as api from "../../modules/api/index"
import CommonConfig from "../../modules/entities/rainfall/CommonConfig"
import RainLevelFilter from "../../modules/entities/rainfall/RainLevelFilter"
import RainLevelSetting from "../../modules/entities/rainfall/RainLevelSetting"
import RainfallChartBuilder from "../../modules/graphic/water/RainfallChartBuilder"
import RainLevelReport from "../../modules/entities/rainfall/RainLevelReport"
import {CHANGE_CHART_SETTING} from "../reducers/Indicators"
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
    rainLevelChart: {
      report: new RainLevelReport(rainLevelChart),
      filter: new RainLevelFilter(rainLevelChart.filter),
      setting: new RainLevelSetting()
    }
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
