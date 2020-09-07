import * as api from "../../modules/api/index"
import CommonConfig from "../../modules/entities/config/CommonConfig"
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
import RainfallChartBuilder from "../../modules/graphic/water/rainfall/RainfallChartBuilder"
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
import {updateCommonConfig} from "./appActions"

export const loadAllWaterData = () => (dispatch, getState) =>
  dispatch({
    type: WATER_RESOURCES,
    payload: api.getAllWaterResources().then(result =>
      transformAll(result, updateCommonConfig(result.commonConfig)(dispatch, getState))
    )
  })

const transformAll = (allData, commonConfig: CommonConfig) => {
  const {rainLevelChart, drySequenceChart, seasonChart, riverLevelChart} = allData
  const waterConfig = new WaterConfig(allData.waterConfig, commonConfig)
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
  const rainfallBuilder = new RainfallChartBuilder(rainLevelChart, intl)

  return {
    rainfallDTO: rainfallBuilder.build()
  }
}

export const setRainSetting = (path, data) => (dispatch, getState) => {
  dispatch({
    type: CHANGE_RAINFALL_SETTING,
    path,
    data
  })
  // retry past filter also on setting change in case it was a temporary connection loss
  if (!getState().getIn(['water', 'isFilteredRainfall'])) {
    const filter: RainLevelFilter = getState().getIn(['water', 'data', 'rainLevelChart', 'filter'])
    return setRainfallFilter(filter.years, filter.pluviometricPostId)(dispatch, getState)
  }
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
  // retry past filter also on setting change in case it was a temporary connection loss
  if (!getState().getIn(['water', 'isFilteredDrySequence'])) {
    const filter: DrySequenceFilter = getState().getIn(['water', 'data', 'drySequenceChart', 'filter'])
    return setDrySequenceFilter(filter.year, filter.pluviometricPostId)(dispatch, getState)
  }
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

  return {
    rainSeasonTableDTO: builder.build()
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
  if (isYearFilter || !getState().getIn(['water', 'isFilteredRainSeason'])) {
    const yearIds = getState().getIn(['water', 'data', 'rainSeasonChart', 'filter', 'yearIds'])
    const year = isYearFilter ? value[0] : yearIds[0]
    return getRainSeasonByYear(year)(dispatch, getState)
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
