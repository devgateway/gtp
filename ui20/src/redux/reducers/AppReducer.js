import Immutable from "immutable"

export const WORLD_MAP_ATTRIBUTION = 'WORLD_MAP_ATTRIBUTION'
const WORLD_MAP_ATTRIBUTION_PENDING = 'WORLD_MAP_ATTRIBUTION_PENDING'
const WORLD_MAP_ATTRIBUTION_FULFILLED = 'WORLD_MAP_ATTRIBUTION_PENDING'
const WORLD_MAP_ATTRIBUTION_REJECTED = 'WORLD_MAP_ATTRIBUTION_PENDING'
export const MENU_TOGGLE = 'MENU_TOGGLE'


const initialState = Immutable.fromJS({
  isLoading: false,
  isLoaded: false,
  error: null,
  isMenuOpened: true,
  data: {
    worldMapAttribution: 'Sources: Esri, HERE, Garmin, USGS, Intermap, INCREMENT P, NRCan, Esri Japan, METI, Esri China (Hong Kong), Esri Korea, Esri (Thailand), NGCC, (c) OpenStreetMap contributors, and the GIS User Community',
  }
})

export default (state = initialState, action) => {
  const { data, payload } = action
  switch (action.type) {
    case WORLD_MAP_ATTRIBUTION_PENDING:
      return state.set('isLoading', true).set('error', null)
    case WORLD_MAP_ATTRIBUTION_FULFILLED:
      return state.set('isLoading', false).set('isLoaded', true).setIn(['data', 'worldMapAttribution'], payload)
    case WORLD_MAP_ATTRIBUTION_REJECTED:
      return state.set('isLoading', false).set('isLoaded', false).set('error', payload)
    case MENU_TOGGLE:
      return state.set('isMenuOpened', data)
    default:
      return state
  }
}
