import Immutable from 'immutable'

export const WATER_RESOURCES = 'WATER_RESOURCES';
const WATER_RESOURCES_PENDING = 'WATER_RESOURCES_PENDING'
const WATER_RESOURCES_FULFILLED = 'WATER_RESOURCES_FULFILLED'
const WATER_RESOURCES_REJECTED = 'WATER_RESOURCES_REJECTED'

const initialState = Immutable.fromJS({
  isLoading: false,
  isLoaded: false,
  error: null,
  data: {}
})

export default (state = initialState, action) => {
  const { data } = action;
  switch (action.type) {
    case WATER_RESOURCES_PENDING:
      return state.set('isLoading', true).set('error', null)
    case WATER_RESOURCES_FULFILLED:
      return state.set('isLoading', false).set('isLoaded', true).set('data', data)
    case WATER_RESOURCES_REJECTED:
      return state.set('isLoading', false).set('isLoaded', false).set('error', data)
    default: {
      return state
    }
  }
}
