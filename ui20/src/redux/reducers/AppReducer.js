import Immutable from "immutable"
import CommonConfig from "../../modules/entities/config/CommonConfig"

export const WORLD_MAP_ATTRIBUTION = 'WORLD_MAP_ATTRIBUTION'
const WORLD_MAP_ATTRIBUTION_PENDING = 'WORLD_MAP_ATTRIBUTION_PENDING'
const WORLD_MAP_ATTRIBUTION_FULFILLED = 'WORLD_MAP_ATTRIBUTION_PENDING'
const WORLD_MAP_ATTRIBUTION_REJECTED = 'WORLD_MAP_ATTRIBUTION_PENDING'
export const MENU_TOGGLE = 'MENU_TOGGLE'
export const COMMON_CONFIG_UPDATE = 'COMMON_CONFIG_UPDATE'
export const CNSC_HEADER = 'CNSC_HEADER'
const CNSC_HEADER_PENDING = 'CNSC_HEADER_PENDING'
const CNSC_HEADER_FULFILLED = 'CNSC_HEADER_FULFILLED'
const CNSC_HEADER_REJECTED = 'CNSC_HEADER_REJECTED'

const initialState = Immutable.fromJS({
  isLoading: false,
  isLoaded: false,
  error: null,
  isMenuOpened: true,
  data: {
    commonConfig: CommonConfig,
    worldMapAttribution: 'Sources: Esri, HERE, Garmin, USGS, Intermap, INCREMENT P, NRCan, Esri Japan, METI, Esri China (Hong Kong), Esri Korea, Esri (Thailand), NGCC, (c) OpenStreetMap contributors, and the GIS User Community',
    cnscHeader: null,
  },
  isCNSCHeaderLoading: false,
  isCNSCHeaderLoaded: false,
})

export default (state = initialState, action) => {
  const { data, payload } = action
  switch (action.type) {
    case COMMON_CONFIG_UPDATE:
      return state.setIn(['data', 'commonConfig'], data)
    case WORLD_MAP_ATTRIBUTION_PENDING:
      return state.set('isLoading', true).set('error', null)
    case WORLD_MAP_ATTRIBUTION_FULFILLED:
      return state.set('isLoading', false).set('isLoaded', true).setIn(['data', 'worldMapAttribution'], payload)
    case WORLD_MAP_ATTRIBUTION_REJECTED:
      return state.set('isLoading', false).set('isLoaded', false).set('error', payload)
    case MENU_TOGGLE:
      return state.set('isMenuOpened', data)
    case CNSC_HEADER_PENDING:
      return state.set('isCNSCHeaderLoading', true).set('isCNSCHeaderLoaded', false).set('error', null)
    case CNSC_HEADER_FULFILLED:
      return state.set('isCNSCHeaderLoading', false).set('isCNSCHeaderLoaded', true)
        .setIn(['data', 'cnscHeader'], payload)
    case CNSC_HEADER_REJECTED:
      return state.set('isCNSCHeaderLoading', false).set('isCNSCHeaderLoaded', false).set('error', payload)
    default:
      return state
  }
}
