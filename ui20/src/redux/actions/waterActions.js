import * as api from "../../modules/api/index"
import CommonConfig from "../../modules/entities/rainfall/CommonConfig"
import RainLevelConfig from "../../modules/entities/rainfall/RainLevelConfig"
import RainLevelData from "../../modules/entities/rainfall/RainLevelData"
import RainLevelFilter from "../../modules/entities/rainfall/RainLevelFilter"
import RainLevelSetting from "../../modules/entities/rainfall/RainLevelSetting"
import {rainSeasonChartFromApi} from "../../modules/entities/rainSeason/RainSeasonChart"
import RainfallChartBuilder from "../../modules/graphic/water/RainfallChartBuilder"
import {CHANGE_RAINFALL_FILTER, CHANGE_RAINFALL_SETTING, FILTER_RAINFALL, WATER_RESOURCES} from "../reducers/Water"

export const loadAllWaterData = () => (dispatch, getState) =>
  dispatch({
    type: WATER_RESOURCES,
    payload: api.getAllWaterResources().then(transformAll)
  })

export const getRainfall = (rainLevelFilter) => (dispatch, getState) =>
  dispatch({
    type: FILTER_RAINFALL,
    payload: api.getRainfall(rainLevelFilter).then(transformRainfall)
  })

const transformAll = (allData) => {
  const {commonConfig, rainLevelChart, seasonChart} = allData
  return {
    commonConfig: new CommonConfig(commonConfig),
    rainLevelChart: {
      config: new RainLevelConfig(rainLevelChart.config),
      data: new RainLevelData(rainLevelChart.data),
      filter: new RainLevelFilter(rainLevelChart.filter.years, rainLevelChart.filter.pluviometricPostId),
      setting: new RainLevelSetting()
    },
    rainSeasonChart: rainSeasonChartFromApi(seasonChart)
  }
}

const transformRainfall = (data) => new RainLevelData(data)

export const getRain = (intl) => (dispatch, getState) => {
  const {rainLevelChart} = getState().getIn(['water', 'data'])
  const rainfallDTO = new RainfallChartBuilder(rainLevelChart, intl)

  rainfallDTO.build()

  return {
    barData: rainfallDTO.barData,
    keys: rainfallDTO.keys.map(k => `${k}`),
    keysWithRefs: rainfallDTO.keyWithReferences,
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

export const setRainPerDecadal = (byDecadal) => (dispatch, getState) => {
  dispatch({
    type: CHANGE_RAINFALL_SETTING,
    data: new RainLevelSetting(byDecadal)
  })
}

export const setFilter = (years: Array<number>, pluviometricPostId: number) => (dispatch, getState) => {
  const rainLevelFilter = new RainLevelFilter(years, pluviometricPostId)
  dispatch({
    type: CHANGE_RAINFALL_FILTER,
    data: rainLevelFilter
  })
  return getRainfall(rainLevelFilter)(dispatch, getState)
}
