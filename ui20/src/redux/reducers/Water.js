import Immutable from 'immutable'

const WATER_RESOURCES_LOADING = 'WATER_RESOURCES_LOADING'
const WATER_RESOURCES_LOADED = 'WATER_RESOURCES_LOADED'
const WATER_RESOURCES_ERROR = 'WATER_RESOURCES_ERROR'

const initialState = Immutable.fromJS({
  isLoading: false,
  isLoaded: false,
  error: null,
  data: {}
})

export default (state = initialState, action) => {
  const { data } = action;
  switch (action.type) {
    case WATER_RESOURCES_LOADING:
      return state.set('isLoading', true).set('error', null)
    case WATER_RESOURCES_LOADED:
      return state.set('isLoading', false).set('isLoaded', true).set('data', data)
    case WATER_RESOURCES_ERROR:
      return state.set('isLoading', false).set('isLoaded', false).set('error', data)
    default: {
      return state
    }
  }
}
