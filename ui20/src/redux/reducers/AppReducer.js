import Immutable from "immutable"
import CommonConfig from "../../modules/entities/config/CommonConfig"

export const MAP_ATTRIBUTION = 'MAP_ATTRIBUTION'
const MAP_ATTRIBUTION_PENDING = 'MAP_ATTRIBUTION_PENDING'
const MAP_ATTRIBUTION_FULFILLED = 'MAP_ATTRIBUTION_FULFILLED'
const MAP_ATTRIBUTION_REJECTED = 'MAP_ATTRIBUTION_REJECTED'
export const MENU_TOGGLE = 'MENU_TOGGLE'
export const COMMON_CONFIG_UPDATE = 'COMMON_CONFIG_UPDATE'
export const CNSC_HEADER = 'CNSC_HEADER'
const CNSC_HEADER_PENDING = 'CNSC_HEADER_PENDING'
const CNSC_HEADER_FULFILLED = 'CNSC_HEADER_FULFILLED'
const CNSC_HEADER_REJECTED = 'CNSC_HEADER_REJECTED'
export const FM_CONFIG = 'FM_CONFIG'
const FM_CONFIG_PENDING = 'FM_CONFIG_PENDING'
const FM_CONFIG_FULFILLED = 'FM_CONFIG_FULFILLED'
const FM_CONFIG_REJECTED = 'FM_CONFIG_REJECTED'

const initialState = Immutable.fromJS({
  isLoading: false,
  isLoaded: false,
  error: null,
  isMenuOpened: true,
  data: {
    commonConfig: CommonConfig,
    mapAttribution: {
      world: 'Sources: Esri, HERE, Garmin, USGS, Intermap, INCREMENT P, NRCan, Esri Japan, METI, Esri China (Hong Kong), Esri Korea, Esri (Thailand), NGCC, (c) OpenStreetMap contributors, and the GIS User Community',
      topo: 'Esri, HERE, DeLorme, TomTom, Intermap, increment P Corp., GEBCO, USGS, FAO, NPS, NRCAN, GeoBase, IGN, Kadaster NL, Ordnance Survey, Esri Japan, METI, Esri China (Hong Kong), swisstopo, MapmyIndia, © OpenStreetMap contributors, GIS User Comm.',
    },
    cnscHeader: null,
    fmConfig: null,
  },
  isCNSCHeaderLoading: false,
  isCNSCHeaderLoaded: false,
  isFMConfigLoading: false,
  isFMConfigLoaded: false,
})

export default (state = initialState, action) => {
  const { data, payload } = action
  switch (action.type) {
    case COMMON_CONFIG_UPDATE:
      return state.setIn(['data', 'commonConfig'], data)
    case MAP_ATTRIBUTION_PENDING:
      return state.set('isLoading', true).set('error', null)
    case MAP_ATTRIBUTION_FULFILLED:
      const {type, copyrightText} = payload
      return state.set('isLoading', false).set('isLoaded', true).setIn(['data', 'mapAttribution', type], copyrightText)
    case MAP_ATTRIBUTION_REJECTED:
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
    case FM_CONFIG_PENDING:
      return state.set('isFMConfigLoading', true).set('isFMConfigLoaded', false).set('error', null)
    case FM_CONFIG_FULFILLED:
      return state.set('isFMConfigLoading', false).set('isFMConfigLoaded', true).setIn(['data', 'fmConfig'], payload)
    case FM_CONFIG_REJECTED:
      return state.set('isFMConfigLoading', false).set('isFMConfigLoaded', false).set('error', payload)
    default:
      return state
  }
}
