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
import PluviometricPostMapBuilder from "../../modules/graphic/water/postMap/PluviometricPostMapBuilder"
import PluviometricPostMapDTO from "../../modules/graphic/water/postMap/PluviometricPostMapDTO"
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
import * as appActions from "./appActions"
import {updateCommonConfig} from "./appActions"
import {transformRainMapFromApi} from "./water/rainMapActions"

export const loadAllWaterData = () => (dispatch, getState) => {
  dispatch({
    type: WATER_RESOURCES,
    payload: api.getAllWaterResources().then(result =>
      transformAll(result, updateCommonConfig(result.commonConfig)(dispatch, getState))
    )
  })
  appActions.loadMapAttribution("world")(dispatch, getState)
}

const transformAll = (allData, commonConfig: CommonConfig) => {
  const {rainLevelChart, rainMap, drySequenceChart, seasonChart, riverLevelChart} = allData
  const waterConfig = new WaterConfig(allData.waterConfig)
  return {
    waterConfig,
    rainLevelChart: {
      config: new RainLevelConfig(rainLevelChart.config),
      data: new RainLevelData(rainLevelChart.data),
      filter: new RainLevelFilter(rainLevelChart.filter.years, rainLevelChart.filter.pluviometricPostId),
      setting: RainLevelSetting,
    },
    rainMap: transformRainMapFromApi(rainMap),
    drySequenceChart: drySequenceChartFromApi(drySequenceChart),
    rainSeasonChart: rainSeasonChartFromApi(waterConfig, commonConfig, seasonChart),
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
  const commonConfig = getState().getIn(['app', 'data', 'commonConfig'])
  const waterConfig = getState().getIn(['water', 'data', 'waterConfig'])
  return dispatch({
    type: FILTER_RAIN_SEASON,
    payload: api.getRainSeason(year).then((data) => transformRainSeason(rainSeasonChart, waterConfig, commonConfig, data))
  })
}

const transformRainSeason = (rainSeasonChart, waterConfig, commonConfig, data) =>
  rainSeasonDataFromApi(rainSeasonChart, waterConfig, commonConfig, rainSeasonChart.config, data)

export const getRainSeason = () => (dispatch, getState) => {
  const { rainSeasonChart, waterConfig } = getState().getIn(['water', 'data'])
  const commonConfig = getState().getIn(['app', 'data', 'commonConfig'])

  const builder = new RainSeasonTableBuilder(rainSeasonChart, waterConfig, commonConfig)

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
    const commonConfig = getState().getIn(['app', 'data', 'commonConfig'])
    return dispatch({
      type: FILTER_RAIN_SEASON,
      payload: handleFilter(rainSeasonChart, waterConfig, commonConfig)
    })
  }
}

/*      POSTS MAP          */
export const getPostLocation = (): PluviometricPostMapDTO => (dispatch, getState) => {
  const waterConfig = getState().getIn(['water', 'data', 'waterConfig'])
  const commonConfig = getState().getIn(['app', 'data', 'commonConfig'])

  return {
    postMapDTO: new PluviometricPostMapBuilder(commonConfig, waterConfig).build()
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
