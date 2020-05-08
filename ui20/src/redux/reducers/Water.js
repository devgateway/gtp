import Immutable from 'immutable'
import CommonConfig from "../../modules/entities/rainfall/CommonConfig";
import RainLevelConfig from "../../modules/entities/rainfall/RainLevelConfig"
import RainLevelData from "../../modules/entities/rainfall/RainLevelData"
import RainLevelFilter from "../../modules/entities/rainfall/RainLevelFilter"
import RainLevelSetting from "../../modules/entities/rainfall/RainLevelSetting"
import {CHANGE_CHART_SETTING} from "./Indicators"

export const WATER_RESOURCES = 'WATER_RESOURCES';
const WATER_RESOURCES_PENDING = 'WATER_RESOURCES_PENDING'
const WATER_RESOURCES_FULFILLED = 'WATER_RESOURCES_FULFILLED'
const WATER_RESOURCES_REJECTED = 'WATER_RESOURCES_REJECTED'

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
  }
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
    case CHANGE_CHART_SETTING:
      return state.setIn(['data', 'rainLevelChart', 'setting'], data)
    default: {
      return state
    }
  }
}
