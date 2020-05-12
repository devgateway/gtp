import Immutable from 'immutable'
import CommonConfig from "../../modules/entities/rainfall/CommonConfig";
import RainLevelConfig from "../../modules/entities/rainfall/RainLevelConfig"
import RainLevelData from "../../modules/entities/rainfall/RainLevelData"
import RainLevelFilter from "../../modules/entities/rainfall/RainLevelFilter"
import RainLevelSetting from "../../modules/entities/rainfall/RainLevelSetting"


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

const initialState = Immutable.fromJS({
  isLoading: false,
  isLoaded: false,
  error: null,
  data: {
    commonConfig: CommonConfig,
    rainLevelChart: {
      config: RainLevelConfig,
      data: RainLevelData,
      filter: RainLevelFilter,
      setting: RainLevelSetting,
    }
  },
  isFilteringRainfall: false,
  isFilteredRainfall: false,
})

export default (state = initialState, action) => {
  const { payload, data } = action;
  switch (action.type) {
    case WATER_RESOURCES_PENDING:
      return state.set('isLoading', true).set('error', null)
    case WATER_RESOURCES_FULFILLED:
      return state.set('isLoading', false).set('isLoaded', true).set('data', payload)
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
      return state.setIn(['data', 'rainLevelChart', 'setting'], data)
    default: {
      return state
    }
  }
}
