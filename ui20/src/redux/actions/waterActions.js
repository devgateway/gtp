import * as api from "../../modules/api/index"
import CommonConfig from "../../modules/entities/rainfall/CommonConfig"
import RainLevelConfig from "../../modules/entities/rainfall/RainLevelConfig"
import RainLevelData from "../../modules/entities/rainfall/RainLevelData"
import RainLevelFilter from "../../modules/entities/rainfall/RainLevelFilter"
import RainLevelSetting from "../../modules/entities/rainfall/RainLevelSetting"
import {rainSeasonChartFromApi} from "../../modules/entities/rainSeason/RainSeasonChart"
import RainSeasonData from "../../modules/entities/rainSeason/RainSeasonData"
import RainfallChartBuilder from "../../modules/graphic/water/RainfallChartBuilder"
import RainSeasonTableBuilder from "../../modules/graphic/water/rainSeason/RainSeasonTableBuilder"
import {
  CHANGE_RAIN_SEASON_FILTER,
  CHANGE_RAINFALL_FILTER,
  CHANGE_RAINFALL_SETTING,
  FILTER_RAIN_SEASON,
  FILTER_RAINFALL,
  SORT_RAIN_SEASON,
  WATER_RESOURCES
} from "../reducers/Water"

export const loadAllWaterData = () => (dispatch, getState) =>
  dispatch({
    type: WATER_RESOURCES,
    payload: api.getAllWaterResources().then(transformAll)
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

/*      RAINFALL          */

export const getRainfall = (rainLevelFilter) => (dispatch, getState) =>
  dispatch({
    type: FILTER_RAINFALL,
    payload: api.getRainfall(rainLevelFilter).then(transformRainfall)
  })

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

export const setRainfallFilter = (years: Array<number>, pluviometricPostId: number) => (dispatch, getState) => {
  const rainLevelFilter = new RainLevelFilter(years, pluviometricPostId)
  dispatch({
    type: CHANGE_RAINFALL_FILTER,
    data: rainLevelFilter
  })
  return getRainfall(rainLevelFilter)(dispatch, getState)
}

/*      RAIN SEASON          */

export const getRainSeasonByYear = (year: number) => (dispatch, getState) =>
  dispatch({
    type: FILTER_RAIN_SEASON,
    payload: api.getRainSeason(year).then((data) => transformRainSeason(data, year))
  })

const transformRainSeason = (data, year)  => new RainSeasonData(data, year)

export const getRainSeason = () => (dispatch, getState) => {
  const { rainSeasonChart, commonConfig } = getState().getIn(['water', 'data'])
  const builder = new RainSeasonTableBuilder(rainSeasonChart, commonConfig)

  builder.build()

  return {
    data: builder.data,
    config: builder.config,
  }
}

export const sortRainSeason = (sortedBy, sortedAsc) => (dispatch, getState) =>
  dispatch({
    type: SORT_RAIN_SEASON,
    data: {sortedBy, sortedAsc}
  })

export const setRainSeasonFilter = (path: Array<string>, value, isYearFilter: boolean) => (dispatch, getState) => {
  dispatch({
    type: CHANGE_RAIN_SEASON_FILTER,
    data: value,
    path
  })
  if (isYearFilter) {
    return getRainSeasonByYear(value[0])(dispatch, getState)
  }
}
