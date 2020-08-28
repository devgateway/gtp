import Immutable from 'immutable'
import DrySequenceChart from "../../modules/entities/drySequence/DrySequenceChart"
import WaterConfig from "../../modules/entities/config/WaterConfig";
import RainLevelConfig from "../../modules/entities/rainfall/RainLevelConfig"
import RainLevelData from "../../modules/entities/rainfall/RainLevelData"
import RainLevelFilter from "../../modules/entities/rainfall/RainLevelFilter"
import RainLevelSetting from "../../modules/entities/rainfall/RainLevelSetting"
import RainSeasonChart from "../../modules/entities/rainSeason/RainSeasonChart"
import RiverLevelChart from "../../modules/entities/river/RiverLevelChart"


export const WATER_RESOURCES = 'WATER_RESOURCES';
const WATER_RESOURCES_PENDING = 'WATER_RESOURCES_PENDING'
const WATER_RESOURCES_FULFILLED = 'WATER_RESOURCES_FULFILLED'
const WATER_RESOURCES_REJECTED = 'WATER_RESOURCES_REJECTED'
export const FILTER_RAINFALL = 'FILTER_RAINFALL'
const FILTER_RAINFALL_PENDING = 'FILTER_RAINFALL_PENDING'
const FILTER_RAINFALL_FULFILLED = 'FILTER_RAINFALL_FULFILLED'
const FILTER_RAINFALL_REJECTED = 'FILTER_RAINFALL_REJECTED'
export const CHANGE_RAINFALL_FILTER = 'CHANGE_RAINFALL_FILTER'
export const CHANGE_RAINFALL_SETTING = 'CHANGE_RAINFALL_SETTING'
export const FILTER_DRY_SEQUENCE = 'FILTER_DRY_SEQUENCE'
const FILTER_DRY_SEQUENCE_PENDING = 'FILTER_DRY_SEQUENCE_PENDING'
const FILTER_DRY_SEQUENCE_FULFILLED = 'FILTER_DRY_SEQUENCE_FULFILLED'
const FILTER_DRY_SEQUENCE_REJECTED = 'FILTER_DRY_SEQUENCE_REJECTED'
export const CHANGE_DRY_SEQUENCE_FILTER = 'CHANGE_DRY_SEQUENCE_FILTER'
export const CHANGE_DRY_SEQUENCE_SETTING = 'CHANGE_DRY_SEQUENCE_SETTING'
export const FILTER_RAIN_SEASON = 'FILTER_RAIN_SEASON'
const FILTER_RAIN_SEASON_PENDING = 'FILTER_RAIN_SEASON_PENDING'
const FILTER_RAIN_SEASON_FULFILLED = 'FILTER_RAIN_SEASON_FULFILLED'
const FILTER_RAIN_SEASON_REJECTED = 'FILTER_RAIN_SEASON_REJECTED'
export const CHANGE_RAIN_SEASON_FILTER = 'CHANGE_RAIN_SEASON_FILTER'
export const SORT_RAIN_SEASON = 'SORT_RAIN_SEASON'
export const FILTER_RIVER_LEVEL = 'FILTER_RIVER_LEVEL'
const FILTER_RIVER_LEVEL_PENDING = 'FILTER_RIVER_LEVEL_PENDING'
const FILTER_RIVER_LEVEL_FULFILLED = 'FILTER_RIVER_LEVEL_FULFILLED'
const FILTER_RIVER_LEVEL_REJECTED = 'FILTER_RIVER_LEVEL_REJECTED'
export const CHANGE_RIVER_LEVEL_FILTER = 'CHANGE_RIVER_LEVEL_FILTER'
export const CHANGE_RIVER_LEVEL_SETTING = 'CHANGE_RIVER_LEVEL_SETTING'

const initialState = Immutable.fromJS({
  isLoading: null,
  isLoaded: null,
  error: null,
  data: {
    waterConfig: new WaterConfig(),
    rainLevelChart: {
      config: new RainLevelConfig(),
      data: RainLevelData,
      filter: new RainLevelFilter(),
      setting: RainLevelSetting,
    },
    drySequenceChart: DrySequenceChart,
    rainSeasonChart: RainSeasonChart,
    riverLevelChart: RiverLevelChart,
  },
  isFilteringRainfall: false,
  isFilteredRainfall: false,
  isFilteringDrySequence: false,
  isFilteredDrySequence: false,
  isFilteringRainSeason: false,
  isFilteredRainSeason: false,
  isFilteringRiverLevel: false,
  isFilteredRiverLevel: false,
})

export default (state = initialState, action) => {
  const { payload, data, path } = action;
  switch (action.type) {
    case WATER_RESOURCES_PENDING:
      return state.set('isLoading', true).set('error', null)
    case WATER_RESOURCES_FULFILLED:
      return state.set('isLoading', false).set('isLoaded', true).set('data', payload)
        .set('isFilteredRainfall', true).set('isFilteredDrySequence', true).set('isFilteredRainSeason', true)
        .set('isFilteredRiverLevel', true)
    case WATER_RESOURCES_REJECTED:
      return state.set('isLoading', false).set('isLoaded', false).set('error', payload)
    case FILTER_RAINFALL_PENDING:
      return state.set('isFilteringRainfall', true).set('isFilteredRainfall', false).set('error', null)
    case FILTER_RAINFALL_FULFILLED:
      return state.set('isFilteringRainfall', false).set('isFilteredRainfall', true)
        .setIn(['data', 'rainLevelChart', 'data'], payload)
    case FILTER_RAINFALL_REJECTED:
      return state.set('isFilteringRainfall', false).set('isFilteredRainfall', false).set('error', payload)
    case CHANGE_RAINFALL_FILTER:
      return state.setIn(['data', 'rainLevelChart', 'filter'], data)
    case CHANGE_RAINFALL_SETTING:
      return state.setIn(['data', 'rainLevelChart', 'setting', ...path], data)
    case FILTER_DRY_SEQUENCE_PENDING:
      return state.set('isFilteringDrySequence', true).set('isFilteredDrySequence', false).set('error', null)
    case FILTER_DRY_SEQUENCE_FULFILLED:
      return state.set('isFilteringDrySequence', false).set('isFilteredDrySequence', true)
        .setIn(['data', 'drySequenceChart', 'data'], payload)
    case FILTER_DRY_SEQUENCE_REJECTED:
      return state.set('isFilteringDrySequence', false).set('isFilteredDrySequence', false).set('error', payload)
    case CHANGE_DRY_SEQUENCE_FILTER:
      return state.setIn(['data', 'drySequenceChart', 'filter'], data)
    case CHANGE_DRY_SEQUENCE_SETTING:
      return state.setIn(['data', 'drySequenceChart', 'settings', 'isDaysWithRain'], data)
    case FILTER_RAIN_SEASON_PENDING:
      return state.set('isFilteringRainSeason', true).set('isFilteredRainSeason', false).set('error', null)
    case FILTER_RAIN_SEASON_FULFILLED:
      return state.set('isFilteringRainSeason', false).set('isFilteredRainSeason', true)
        .setIn(['data', 'rainSeasonChart'], payload)
    case FILTER_RAIN_SEASON_REJECTED:
      return state.set('isFilteringRainSeason', false).set('isFilteredRainSeason', false).set('error', payload)
    case CHANGE_RAIN_SEASON_FILTER:
      return state.setIn(path, data)
    case SORT_RAIN_SEASON: {
      const {sortedBy, sortedAsc} = data
      return state.setIn(['data', 'rainSeasonChart', 'sortedBy'], sortedBy)
        .setIn(['data', 'rainSeasonChart', 'sortedAsc'], sortedAsc)
    }
    case FILTER_RIVER_LEVEL_PENDING:
      return state.set('isFilteringRiverLevel', true).set('isFilteredRiverLevel', false).set('error', null)
    case FILTER_RIVER_LEVEL_FULFILLED:
      return state.set('isFilteringRiverLevel', false).set('isFilteredRiverLevel', true)
        .setIn(['data', 'riverLevelChart', 'data'], payload)
    case FILTER_RIVER_LEVEL_REJECTED:
      return state.set('isFilteringRiverLevel', false).set('isFilteredRiverLevel', false).set('error', payload)
    case CHANGE_RIVER_LEVEL_FILTER:
      return state.setIn(['data', 'riverLevelChart', 'filter', ...path], data)
    case CHANGE_RIVER_LEVEL_SETTING:
      return state.setIn(['data', 'riverLevelChart', 'setting', 'showAlert'], data)
    default: {
      return state
    }
  }
}
