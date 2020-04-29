import {
  combineReducers
} from 'redux-immutable'

import {
  connectRouter
} from 'connected-react-router/immutable'

import water from './Water'

const createRootReducer = (history) => combineReducers({
  router: connectRouter(history),
  water,
})

export default createRootReducer
