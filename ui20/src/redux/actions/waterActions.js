import * as api from "../../modules/api/index"
import {drySequenceChartFromApi} from "../../modules/entities/drySequence/DrySequenceChart"
import DrySequenceData from "../../modules/entities/drySequence/DrySequenceData"
import DrySequenceFilter from "../../modules/entities/drySequence/DrySequenceFilter"
import WaterConfig from "../../modules/entities/config/WaterConfig"
import RainLevelConfig from "../../modules/entities/rainfall/RainLevelConfig"
import RainLevelData from "../../modules/entities/rainfall/RainLevelData"
import RainLevelFilter from "../../modules/entities/rainfall/RainLevelFilter"
import RainLevelSetting from "../../modules/entities/rainfall/RainLevelSetting"
import {
  handleFilter,
  rainSeasonChartFromApi,
  rainSeasonDataFromApi
} from "../../modules/entities/rainSeason/RainSeasonChart"
import {riverLevelFromApi} from "../../modules/entities/river/RiverLevelChart"
import RiverLevelData from "../../modules/entities/river/RiverLevelData"
import DrySequenceChartBuilder from "../../modules/graphic/water/drySequence/DrySequenceChartBuilder"
import RainfallChartBuilder from "../../modules/graphic/water/RainfallChartBuilder"
import RainSeasonTableBuilder from "../../modules/graphic/water/rainSeason/RainSeasonTableBuilder"
import RiverLevelChartBuilder from "../../modules/graphic/water/river/RiverLevelChartBuilder"
import RiverLevelChartDTO from "../../modules/graphic/water/river/RiverLevelChartDTO"
import {
  CHANGE_DRY_SEQUENCE_FILTER,
  CHANGE_DRY_SEQUENCE_SETTING,
  CHANGE_RAIN_SEASON_FILTER,
  CHANGE_RAINFALL_FILTER,
  CHANGE_RAINFALL_SETTING,
  CHANGE_RIVER_LEVEL_FILTER,
  CHANGE_RIVER_LEVEL_SETTING,
  FILTER_DRY_SEQUENCE,
  FILTER_RAIN_SEASON,
  FILTER_RAINFALL,
  FILTER_RIVER_LEVEL,
  SORT_RAIN_SEASON,
  WATER_RESOURCES
} from "../reducers/Water"

export const loadAllWaterData = () => (dispatch, getState) =>
  dispatch({
    type: WATER_RESOURCES,
    payload: api.getAllWaterResources().then(transformAll)
  })

const transformAll = (allData) => {
  const {rainLevelChart, drySequenceChart, seasonChart, riverLevelChart} = allData
  const waterConfig = new WaterConfig(allData.waterConfig, allData.commonConfig)
  return {
    waterConfig,
    rainLevelChart: {
      config: new RainLevelConfig(rainLevelChart.config),
      data: new RainLevelData(rainLevelChart.data),
      filter: new RainLevelFilter(rainLevelChart.filter.years, rainLevelChart.filter.pluviometricPostId),
      setting: RainLevelSetting,
    },
    drySequenceChart: drySequenceChartFromApi(drySequenceChart),
    rainSeasonChart: rainSeasonChartFromApi(waterConfig, seasonChart),
    riverLevelChart: riverLevelFromApi(riverLevelChart),
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
    monthDecadal: rainfallDTO.monthDecadal,
  }
}

export const setRainSetting = (path, data) => (dispatch, getState) => {
  dispatch({
    type: CHANGE_RAINFALL_SETTING,
    path,
    data
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

/*      DRY SEQUENCE          */
export const getLengthOfDrySequence = (drySequenceFilter: DrySequenceFilter) => (dispatch, getState) =>
  dispatch({
    type: FILTER_DRY_SEQUENCE,
    payload: api.getLengthOfDrySequence(drySequenceFilter).then(data => new DrySequenceData(data))
  })

export const getDrySequence = (intl) => (dispatch, getState) => {
  const {drySequenceChart} = getState().getIn(['water', 'data'])
  const builder = new DrySequenceChartBuilder(drySequenceChart, intl)

  return builder.build()
}

export const showDaysWithRain = (isDaysWithRain) => (dispatch, getState) => {
  dispatch({
    type: CHANGE_DRY_SEQUENCE_SETTING,
    data: isDaysWithRain
  })
}

export const setDrySequenceFilter = (year: number, pluviometricPostId: number) => (dispatch, getState) => {
  const data = {year, pluviometricPostId}
  dispatch({
    type: CHANGE_DRY_SEQUENCE_FILTER,
    data
  })
  return getLengthOfDrySequence(data)(dispatch, getState)
}

/*      RAIN SEASON          */

export const getRainSeasonByYear = (year: number) => (dispatch, getState) => {
  const rainSeasonChart = getState().getIn(['water', 'data', 'rainSeasonChart'])
  const waterConfig = getState().getIn(['water', 'data', 'waterConfig'])
  return dispatch({
    type: FILTER_RAIN_SEASON,
    payload: api.getRainSeason(year).then((data) => transformRainSeason(rainSeasonChart, waterConfig, data))
  })
}

const transformRainSeason = (rainSeasonChart, waterConfig, data) =>
  rainSeasonDataFromApi(rainSeasonChart, waterConfig, rainSeasonChart.config, data)

export const getRainSeason = () => (dispatch, getState) => {
  const { rainSeasonChart, waterConfig } = getState().getIn(['water', 'data'])
  const builder = new RainSeasonTableBuilder(rainSeasonChart, waterConfig)

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
  } else {
    const rainSeasonChart = getState().getIn(['water', 'data', 'rainSeasonChart'])
    const waterConfig = getState().getIn(['water', 'data', 'waterConfig'])
    return dispatch({
      type: FILTER_RAIN_SEASON,
      payload: handleFilter(rainSeasonChart, waterConfig)
    })
  }
}

/*      RIVER LEVEL          */
export const getRiverLevel = (): RiverLevelChartDTO => (dispatch, getState) => {
  const { riverLevelChart } = getState().getIn(['water', 'data'])
  const builder = new RiverLevelChartBuilder(riverLevelChart)

  return {
    data: builder.build(),
  }
}

export const showAlert = (isShow: boolean) => (dispatch, getState) => dispatch({
  type: CHANGE_RIVER_LEVEL_SETTING,
  data: isShow
})

const getRiverLevelByFilter = (dispatch, getState) => {
  const filter = getState().getIn(['water', 'data', 'riverLevelChart', 'filter'])
  return dispatch({
    type: FILTER_RIVER_LEVEL,
    payload: api.getRiverLevel(filter).then(data => new RiverLevelData(data))
  })
}

export const setRiverLevelFilter = (path, data) => (dispatch, getState) => {
  dispatch({
    type: CHANGE_RIVER_LEVEL_FILTER,
    path,
    data
  })
  getRiverLevelByFilter(dispatch, getState)
}
